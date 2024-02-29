package alekseybykov.pets.mg.gui.components.listeners.menu;

import alekseybykov.pets.mg.core.businessobjects.message.OAGISMessage;
import alekseybykov.pets.mg.core.models.table.TBMessageTableModel;
import alekseybykov.pets.mg.core.sax.OAGISMessageHandler;
import alekseybykov.pets.mg.core.ticket.TicketFactory;
import alekseybykov.pets.mg.core.ticket.TicketType;
import alekseybykov.pets.mg.gui.components.progressbar.TBMessageProgressBar;
import alekseybykov.pets.mg.gui.components.tables.tbmessage.TBMessageTable;
import alekseybykov.pets.mg.services.tbmessage.TBMessageService;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author bykov.alexey
 * @since 26.02.2024
 */
@Component
public class SendConfirmBodActionListener implements ActionListener {

	@Autowired
	private TBMessageTableModel tableModel;
	@Autowired
	private  TBMessageTable table;
	@Autowired
	private TBMessageProgressBar progressBar;

	@Autowired
	private TBMessageService service;
	@Autowired
	private TicketFactory ticketFactory;

	@Override
	public void actionPerformed(ActionEvent e) {
		val selectedRow = table.getSelectedRow();
		if (selectedRow != NumberUtils.INTEGER_MINUS_ONE) {

			progressBar.setVisible(true);

			val recordId = Integer.parseInt((String) table.getValueAt(selectedRow, NumberUtils.INTEGER_ZERO));
			SwingWorker<Boolean, Void> blobLoader = new SwingWorker<Boolean, Void>() {
				@Override
				@SneakyThrows
				protected Boolean doInBackground() {
					val xml = service.findFileById(recordId, StandardCharsets.UTF_8);
					OAGISMessage oagisMessage = OAGISMessageHandler.createDefault().handle(xml);
					File confirmBod = ticketFactory.generateTicketAsResponse(TicketType.CONFIRM_BOD, oagisMessage);
					// todo
					return true;
				}

				@Override
				@SneakyThrows
				protected void done() {
					progressBar.setVisible(false);
				}
			};
			blobLoader.execute();
		}
	}
}
