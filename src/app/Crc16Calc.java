package app;

import java.util.ArrayList;
import java.util.List;

public class Crc16Calc {
	final int P_16 = 0x5b9a;
	static int[] crc_tab16 = new int[256];
	Crc16Calc() {
		initCrc16Tab();
	}

	int MakeCrc(List<Byte> data, int size)
	{
		int crc = 0;  //normal crc 16 or 0xffff for modbus crc
//		char *ptr = (char*) data;
		int data16, tmp;
//		int i;
//		if (!crc_tab16_init)
//			initCrc16Tab();
		for (byte e: data)
		{
			data16 = 0x00ff & e;
			tmp = crc ^ data16;
			crc = (crc >> 8) ^ crc_tab16[tmp & 0xff];
		}
		return crc;
	}

	void initCrc16Tab()
	{
		int i, j;
		int crc, c;

		for (i = 0; i < 256; i++)
		{

			crc = 0;
			c = i;

			for (j = 0; j < 8; j++)
			{

				if (((crc ^ c) & 0x0001) > 0)
					crc = (crc >> 1) ^ P_16;
				else
					crc = crc >> 1;

				c = c >> 1;
			}
			crc_tab16[i] = crc;
		}
	}
	
	public static void main(String[] args) {
		List<Byte> lista = new ArrayList<Byte>();
		lista.add((byte)01);
		lista.add((byte)02);
		lista.add((byte)03);
		Crc16Calc main = new Crc16Calc();
		System.out.println(String.format("0x%04X", main.MakeCrc(lista, 3)));
		System.out.println(String.format("%02X", 97));

	}

}
