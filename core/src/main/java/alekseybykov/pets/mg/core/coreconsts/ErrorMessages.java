package alekseybykov.pets.mg.core.coreconsts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorMessages {

	ADD_NEW_CONFIG_ERROR("Ошибка добавления новой конфигурации"),
	EDIT_CONFIG_ERROR("Ошибка редактирования конфигурации"),
	REMOVE_CONFIG_ERROR("Ошибка удаления конфигурации"),
	CONFIG_COLLISION_ERROR_TEXT("Данная конфигурация уже существует в системе.\nКонфигурации должны быть уникальны."),
	CONFIG_EMPTY_FIELDS_ERROR_TEXT("Конфигурация содержит пустое поле.\nВсе поля конфигурации обязательны для заполнения."),
	CONFIG_NOT_SELECTED_FOR_EDIT_ERROR_TEXT("Конфигурация не выбрана.\nСледует выбрать конфигурацию для редактирования."),
	CONFIG_NOT_SELECTED_FOR_REMOVE_ERROR_TEXT("Конфигурация не выбрана.\nСледует выбрать конфигурацию для удаления."),
	CONFIG_ILLEGAL_NAME_ERROR_TEXT("В имени конфигурации содержатся символы кириллицы."),

	EDIT_LOCAL_PATH_ERROR("Ошибка редактирования значения локального пути"),
	ILLEGAL_CHARACTERS_ERROR("Пусть содержит символы кириллицы."),
	EMPTY_FIELD_ERROR("Не указано значение локального пути."),

	SINGLE_INSTANCE_CHECKER_ERROR_TITLE("MeesageGenerator уже выполняется"),
	SINGLE_INSTANCE_CHECKER_ERROR_TEXT("Выполнение нескольких экземпляров приложения не допускается."),

	FILE_CHOOSER_ERROR_MESSAGE("Файл не найден.\nПроверьте правильность имени файла и повторите попытку");

	@Getter
	private final String name;
}
