package alekseybykov.pets.mg.core.jobs;

import alekseybykov.pets.mg.core.businessobjects.serverlog.ServerLog;
import alekseybykov.pets.mg.core.businessobjects.serverlog.ServerLogReaderSwitch;
import alekseybykov.pets.mg.core.filereaders.reverse.ReverseReader;
import alekseybykov.pets.mg.core.models.complexmodels.ServerToolbarModel;
import alekseybykov.pets.mg.core.models.encoding.ServerLogEncodingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ServerLogReaderJob {

	@Autowired
	@Qualifier("ReverseBytesReader")
	private ReverseReader logReader;
	@Autowired
	private ServerLog serverLog;

	@Autowired
	private ServerLogReaderSwitch readerSwitch;
	@Autowired
	private ServerToolbarModel serverToolbarModel;
	@Autowired
	private ServerLogEncodingModel logEncodingModel;

	@Scheduled(fixedRate = 4000)
	public void task() {
		if (readerSwitch.isEnabled()) {
			String logFileContent = logReader.read(
					serverToolbarModel.getLogfileAbsolutePath(),
					logEncodingModel.getSelectedEncoding());

			serverLog.setLogText(logFileContent);
		}
	}
}
