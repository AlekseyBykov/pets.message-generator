package alekseybykov.pets.mg.dao.mappers.tbbigattr;

import alekseybykov.pets.mg.core.coreconsts.BufferSizes;
import alekseybykov.pets.mg.core.utils.bytes.ByteUtils;
import lombok.SneakyThrows;
import lombok.val;
import lombok.var;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.Arrays;

@Component
public class TBMessageBigAttributesBlobMapper implements RowMapper<byte[]> {

	@Override
	@SneakyThrows
	public byte[] mapRow(ResultSet resultSet, int rowNumber) {
		var dataBuffer = new byte[BufferSizes.TWO_MB.getSize()];

		int counter = -1;
		byte consumedByte;

		Blob blobColumn = resultSet.getBlob("body");

		InputStream blobInputStream = blobColumn.getBinaryStream();
		while (-1 != (consumedByte = (byte) blobInputStream.read())) {
			if (++counter > BufferSizes.TWO_MB.getSize() - 1) {
				Arrays.fill(dataBuffer, (byte) 0);
				val errorMessage = "Максимальный размер отображаемого файла составляет "
						+ BufferSizes.TWO_MB.getName() + ".\n" +
						"Размер запрашиваемого файла превышает максимальный.";

				return errorMessage.getBytes(StandardCharsets.UTF_8);
			}

			dataBuffer[counter] = consumedByte;
		}
		return ByteUtils.removeEmptyTrailingBytes(dataBuffer);
	}
}
