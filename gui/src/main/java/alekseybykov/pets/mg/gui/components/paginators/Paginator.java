package alekseybykov.pets.mg.gui.components.paginators;

import alekseybykov.pets.mg.core.businessobjects.PageableData;
import alekseybykov.pets.mg.core.models.table.TableModel;
import alekseybykov.pets.mg.gui.uiconsts.Paginators;
import alekseybykov.pets.mg.services.paginator.PageableDataService;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.math.NumberUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

public class Paginator {

	@Getter @Setter
	private JPanel paginatorPanel;

	@Getter @Setter
	protected int totalRowCount;
	@Getter @Setter
	protected int displayedButtonsNumber;
	@Getter @Setter
	protected int pageSize;
	@Getter @Setter
	protected int currentSelectedPage;

	private int startRownum;
	private int endRownum;

	private JProgressBar progressBar;
	private TableModel tableModel;
	private PageableDataService service;

	public Paginator(PageableDataService service,
	                 TableModel tableModel,
	                 JProgressBar progressBar) {
		this.service = service;
		this.tableModel = tableModel;
		this.progressBar = progressBar;
	}

	public void init() {
		currentSelectedPage = NumberUtils.INTEGER_ONE;
		displayedButtonsNumber = Paginators.DISPLAYED_BUTTONS_NUMBER.getValue();
		pageSize = Paginators.PAGE_SIZE.getValue();
		startRownum = NumberUtils.INTEGER_ZERO;
		endRownum = NumberUtils.INTEGER_ZERO;
	}

	public void buildPaginatorPanel(int totalRowCount) {
		paginatorPanel.removeAll();

		ButtonGroup buttonGroup = new ButtonGroup();

		int pages = (int) Math.ceil((double) totalRowCount / pageSize);

		if (pages > displayedButtonsNumber) {
			addPagingButton(buttonGroup, 1);

			if (currentSelectedPage > (pages - ((displayedButtonsNumber + 1) / 2))) {
				// 1 ... n->lastPage

				paginatorPanel.add(buildRangeMark());
				addPagingButtonsRange(buttonGroup, pages - displayedButtonsNumber + 3, pages);
			} else if (currentSelectedPage <= (displayedButtonsNumber + 1) / 2) {
				// 1->n ... lastPage

				addPagingButtonsRange(buttonGroup, 2, displayedButtonsNumber - 2);
				paginatorPanel.add(buildRangeMark());
				addPagingButton(buttonGroup, pages);
			} else {
				//1 ... x->n ... lastPage

				paginatorPanel.add(buildRangeMark());

				int start = currentSelectedPage - (displayedButtonsNumber - 4) / 2;
				int end = start + displayedButtonsNumber - 5;

				addPagingButtonsRange(buttonGroup, start, end);
				paginatorPanel.add(buildRangeMark());
				addPagingButton(buttonGroup, pages);
			}
		} else {
			addPagingButtonsRange(buttonGroup, 1, pages);
		}
	}

	@SneakyThrows
	private void paginate() {
		loadPaginatorData(progressBar);
	}

	private void loadPaginatorData(final JProgressBar progressBar) {
		progressBar.setVisible(true);
		SwingWorker<Boolean, Void> dataLoader = new SwingWorker<Boolean, Void>() {

			int totalRowCount;

			@Override
			protected Boolean doInBackground() {
				totalRowCount = service.findRowsCount();
				return true;
			}

			@Override
			@SneakyThrows
			protected void done() {
				if (get() == Boolean.TRUE) {
					int startIndex = (currentSelectedPage - 1) * pageSize;
					int endIndex = startIndex + pageSize;

					if (endIndex > totalRowCount) {
						endIndex = totalRowCount;
					}

					buildPaginatorPanel(totalRowCount);

					startRownum = startIndex;
					endRownum = endIndex;

					loadTableDataRange();
				}
			}
		};
		dataLoader.execute();
	}

	private void loadTableDataRange() {
		SwingWorker<Boolean, Void> dataLoader = new SwingWorker<Boolean, Void>() {
			List<PageableData> rows = Collections.emptyList();

			@Override
			protected Boolean doInBackground() {
				rows = service.findRowsRange(startRownum, endRownum);
				return true;
			}

			@Override
			@SneakyThrows
			protected void done() {
				try {
					if (get() == Boolean.TRUE) {
						tableModel.setData(rows);
						tableModel.updateTable();
					}
				} finally {
					progressBar.setVisible(false);
				}
			}
		};
		dataLoader.execute();
	}

	private JLabel buildRangeMark() {
		return new JLabel("...", SwingConstants.CENTER);
	}

	private void addPagingButtonsRange(ButtonGroup buttonGroup, int start, int end) {
		while (start <= end) {
			addPagingButton(buttonGroup, start);
			start++;
		}
	}

	private void addPagingButton(ButtonGroup buttonGroup, int pageNumber) {
		JToggleButton toggleButton = new JToggleButton(Integer.toString(pageNumber));
		toggleButton.setMargin(new Insets(1, 3, 1, 3));
		toggleButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		buttonGroup.add(toggleButton);

		if (pageNumber == currentSelectedPage) {
			toggleButton.setSelected(true);
		}

		toggleButton.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				currentSelectedPage = Integer.parseInt(actionEvent.getActionCommand());
				paginate();
			}
		});

		paginatorPanel.add(toggleButton);
	}
}
