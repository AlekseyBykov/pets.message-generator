package alekseybykov.pets.mg.gui.components;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.businessobjects.TransportFolder;
import alekseybykov.pets.mg.core.db.h2.H2ConnectionManager;
import alekseybykov.pets.mg.core.db.oracle.OracleConnectionSwitcher;
import alekseybykov.pets.mg.core.logging.LogPanel;
import alekseybykov.pets.mg.core.models.combobox.TransportFolderComboBoxModel;
import alekseybykov.pets.mg.core.models.table.DBConnectionConfigsTableModel;
import alekseybykov.pets.mg.core.models.table.GuiThemeTableModel;
import alekseybykov.pets.mg.core.models.table.LocalPathsTableModel;
import alekseybykov.pets.mg.core.utils.html.HtmlWrapper;
import alekseybykov.pets.mg.gui.components.helpers.GUIHelper;
import alekseybykov.pets.mg.gui.components.panels.oebs_export.OEBSExportTab;
import alekseybykov.pets.mg.gui.components.panels.oebs_import.OEBSImportTab;
import alekseybykov.pets.mg.gui.components.panels.queuein.IncomeQueueBrowserTab;
import alekseybykov.pets.mg.gui.components.panels.queueout.OutcomeQueueBrowserTab;
import alekseybykov.pets.mg.gui.components.panels.serverlog.ServerLoggerTab;
import alekseybykov.pets.mg.gui.components.panels.sqllog.SqlLogBrowserTab;
import alekseybykov.pets.mg.gui.components.panels.tbbigattr.TBMessageBigAttributesBrowserTab;
import alekseybykov.pets.mg.gui.components.panels.tbmessage.TBMessageBrowserTab;
import alekseybykov.pets.mg.gui.uiconsts.GUISize;
import alekseybykov.pets.mg.gui.uiconsts.app.Application;
import alekseybykov.pets.mg.gui.components.menus.MenuBar;
import alekseybykov.pets.mg.gui.uiconsts.localresources.IconPaths;
import alekseybykov.pets.mg.gui.uiconsts.tabs.TabTitles;
import alekseybykov.pets.mg.services.dbconnection.DBConnectionService;
import alekseybykov.pets.mg.services.filefolder.FileFolderService;
import alekseybykov.pets.mg.services.guitheme.GuiThemeService;
import alekseybykov.pets.mg.services.h2updater.H2UpdaterService;
import alekseybykov.pets.mg.services.localpath.LocalPathService;
import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.util.List;

/**
 * @author bykov.alexey
 * @since 29.04.2021
 */
@Component
public class MainFrame extends JFrame {

	@Autowired
	private OEBSExportTab oebsExportTab;
	@Autowired
	private OEBSImportTab oebsImportTab;
	@Autowired
	private TBMessageBrowserTab tbMessageBrowserTab;
	@Autowired
	private TBMessageBigAttributesBrowserTab tbMessageBigAttributesBrowserTab;
	@Autowired
	private IncomeQueueBrowserTab incomeQueueBrowserTab;
	@Autowired
	private OutcomeQueueBrowserTab outcomeQueueBrowserTab;
	@Autowired
	private SqlLogBrowserTab sqlMonitoringTab;
	@Autowired
	private ServerLoggerTab serverLoggerTab;

	@Autowired
	private DBConnectionService dbConnectionService;
	@Autowired
	private DBConnectionConfigsTableModel dbConnectionConfigsTableModel;

	@Autowired
	private LocalPathsTableModel localPathsTableModel;
	@Autowired
	private LocalPathService localPathService;

	@Autowired
	private TransportFolderComboBoxModel transportFolderComboBoxModel;
	@Autowired
	private FileFolderService fileFolderService;
	@Autowired
	private H2UpdaterService h2UpdaterService;

	@Autowired
	private GuiThemeTableModel guiThemeTableModel;
	@Autowired
	private GuiThemeService guiThemeService;

	@Autowired
	private LogPanel logPanel;

	@Autowired
	private MenuBar menuBar;

	public MainFrame() {
		super(String.format("%s %s %s  %s",
				Application.APPLICATION_NAME.getTitle(),
				Application.APPLICATION_VERSION.getTitle(),
				Application.VENDOR_NAME.getTitle(),
				Application.PLATFORM_DETAILS.getTitle())
		);
	}

	@SneakyThrows
	@PostConstruct
	private void postConstruct() {
		applyUpdates();

		loadDBConnectionsConfigs();
		loadLocalPaths();
		loadTransportFolders();
		loadGuiThemes();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(GUISize.APPLICATION_WIDTH.getValue(), GUISize.APPLICATION_HEIGHT.getValue());
		setJMenuBar(menuBar);

		setApplicationIcon();

		buildApplicationLayout();

		setLookAndFeel();

		setVisible(true);
	}

	private void buildApplicationLayout() {
		setLayout(new BorderLayout());

		JTabbedPane tabbedPane = buildTabbedPane();
		final JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tabbedPane, logPanel);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				splitPane.setOneTouchExpandable(true);
				splitPane.setDividerLocation(.7);
			}
		});

		add(splitPane, BorderLayout.CENTER);
	}

	// todo replace conditional dispatcher with command
	@SneakyThrows
	private void setLookAndFeel() {
		LookAndFeel theme = new MetalLookAndFeel();
		val activeTheme = guiThemeService.getActiveTheme();
		if (StringUtils.equals("Metal", activeTheme)) {
			theme = new MetalLookAndFeel();
		} else if (StringUtils.equals("Nimbus", activeTheme)) {
			theme = new NimbusLookAndFeel();
		} else if (StringUtils.equals("Windows", activeTheme)) {
			theme = new WindowsLookAndFeel();
		}

		UIManager.setLookAndFeel(theme);
		SwingUtilities.updateComponentTreeUI(this);
	}

	private JTabbedPane buildTabbedPane() {
		JTabbedPane applicationTabs = new JTabbedPane();

		applicationTabs.add(HtmlWrapper.wrapText(TabTitles.OEBS_EXCHANGE_TAB.getTitle()), buildOeBSExchangeTabs());
		applicationTabs.setTabComponentAt(0, GUIHelper.buildLabelWithImage(TabTitles.OEBS_EXCHANGE_TAB.getTitle(),
				IconPaths.TAB_ICON.getPath(), 12, 12));

		applicationTabs.add(HtmlWrapper.wrapText(TabTitles.PACKET_QUEUE_TAB.getTitle()), buildPacketQueueTabs());
		applicationTabs.setTabComponentAt(1, GUIHelper.buildLabelWithImage(TabTitles.PACKET_QUEUE_TAB.getTitle(),
				IconPaths.TAB_ICON.getPath(), 12, 12));

		applicationTabs.add(HtmlWrapper.wrapText("SQL лог"), sqlMonitoringTab);
		applicationTabs.setTabComponentAt(2, GUIHelper.buildLabelWithImage("SQL лог", IconPaths.TAB_ICON.getPath(), 12, 12));

		applicationTabs.add(HtmlWrapper.wrapText("Серверный лог"), serverLoggerTab);
		applicationTabs.setTabComponentAt(3, GUIHelper.buildLabelWithImage("Серверный лог", IconPaths.TAB_ICON.getPath(), 12, 12));

		applicationTabs.setBackground(null);
		applicationTabs.setFocusable(false);

		return applicationTabs;
	}

	private JTabbedPane buildPacketQueueTabs() {
		JTabbedPane packetQueueTabs = new JTabbedPane();
		packetQueueTabs.add(HtmlWrapper.wrapText(TabTitles.INCOME_QUEUE_TAB.getTitle()), incomeQueueBrowserTab);
		packetQueueTabs.add(HtmlWrapper.wrapText(TabTitles.OUTCOME_QUEUE_TAB.getTitle()), outcomeQueueBrowserTab);
		packetQueueTabs.setBackground(null);
		packetQueueTabs.setFocusable(false);
		return packetQueueTabs;
	}

	private JTabbedPane buildOeBSExchangeTabs() {
		JTabbedPane oebsExchangeTabs = new JTabbedPane();
		oebsExchangeTabs.add(HtmlWrapper.wrapText(TabTitles.OEBS_EXPORT_TAB.getTitle()), oebsExportTab);
		oebsExchangeTabs.add(HtmlWrapper.wrapText(TabTitles.OEBS_IMPORT_TAB.getTitle()), oebsImportTab);
		oebsExchangeTabs.add(HtmlWrapper.wrapText(TabTitles.TB_MESSAGE_TAB_PANEL.getTitle()), tbMessageBrowserTab);
		oebsExchangeTabs.add(HtmlWrapper.wrapText(TabTitles.TB_MESSAGE_BIG_ATTRIBUTES_TAB_PANEL.getTitle()), tbMessageBigAttributesBrowserTab);
		oebsExchangeTabs.setBackground(null);
		oebsExchangeTabs.setFocusable(false);
		return oebsExchangeTabs;
	}

	private void setApplicationIcon() {
		//setIconImage(GUIHelper.getImageIconByPath(IconPaths.APPLICATION_ICON.getPath()).getImage());
	}

	private void applyUpdates() {
		h2UpdaterService.applyUpdates();
	}

	/**
	 * Метод вынесен из {@link SwingWorker} в EDT, поскольку до выполнения запросов к БД H2
	 * необходимо создать ее в {@link H2ConnectionManager#connect}, если она не была создана ранее.
	 * (Вообще, следует реализовать более гибкий функционал подключения и инициализации/обновления БД H2).
	 */
	private void loadDBConnectionsConfigs() {
		List<PageableData> configs = dbConnectionService.getAllConfigs();
		dbConnectionConfigsTableModel.setData(configs);
		OracleConnectionSwitcher.getInstance().setConfigs(configs);
	}
	/**
	 * Метод вынесен из {@link SwingWorker} в EDT, поскольку до выполнения запросов к БД H2
	 * необходимо создать ее в {@link H2ConnectionManager#connect}, если она не была создана ранее.
	 * (Вообще, следует реализовать более гибкий функционал подключения и инициализации/обновления БД H2).
	 */
	private void loadLocalPaths() {
		List<PageableData> localPaths = localPathService.getAllLocalPaths();
		localPathsTableModel.setData(localPaths);
	}
	/**
	 * Метод вынесен из {@link SwingWorker} в EDT, поскольку до выполнения запросов к БД H2
	 * необходимо создать ее в {@link H2ConnectionManager#connect}, если она не была создана ранее.
	 * (Вообще, следует реализовать более гибкий функционал подключения и инициализации/обновления БД H2).
	 */
	private void loadTransportFolders() {
		val rootPath = localPathService.getLocalPathValue("TRANSPORT_HOME");
		List<TransportFolder> transportFolders = fileFolderService.findAllSubFolders(rootPath);
		transportFolderComboBoxModel.setData(transportFolders);
	}
	/**
	 * Метод вынесен из {@link SwingWorker} в EDT, поскольку до выполнения запросов к БД H2
	 * необходимо создать ее в {@link H2ConnectionManager#connect}, если она не была создана ранее.
	 * (Вообще, следует реализовать более гибкий функционал подключения и инициализации/обновления БД H2).
	 */
	private void loadGuiThemes() {
		List<PageableData> guiThemes = guiThemeService.getAllThemes();
		guiThemeTableModel.setData(guiThemes);
	}
}
