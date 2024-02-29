package alekseybykov.pets.mg.gui.uiconsts.localresources;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author bykov.alexey
 * @since 26.12.2022
 */
@RequiredArgsConstructor
public enum IconPaths {

	//APPLICATION_ICON(""),
	TAB_ICON("/icons/tabs/tab.png"),
	CHOOSE_XML_BTN_ICON("/icons/buttons/upload.png"),
	ADD_ATTACHMENT_BTN_ICON("/icons/buttons/upload.png"),
	SEND_XML_BTN_ICON("/icons/buttons/generate.png"),
	REFRESH_BTN_ICON("/icons/buttons/refresh.png"),
	SEARCH_BTN_ICON("/icons/buttons/search.png"),
	ADD_NEW_BTN_ICON("/icons/buttons/add.png"),
	EDIT_BTN_ICON("/icons/buttons/edit.png"),
	REMOVE_BTN_ICON("/icons/buttons/remove.png"),
	OK_BTN_ICON("/icons/buttons/ok.png"),
	CANCEL_BTN_ICON("/icons/buttons/cancel.png");
	//MSG_GEN_IMAGE("/images/about_the_program_header.jpg");

	@Getter
	private final String path;
}
