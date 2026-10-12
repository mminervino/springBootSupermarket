package com.xantrix.webapp.UnitTest.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
 
import com.xantrix.webapp.Application;
import com.xantrix.webapp.barcode.Barcode;
import com.xantrix.webapp.barcode.BarcodeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.junit.jupiter.api.Test;

@ContextConfiguration(classes = Application.class)
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class BarcodeRepositoryTest
{
	private final BarcodeRepository barcodeRepository;
	
	
	@Test
	public void TestfindByBarcode()
	{
		assertThat(barcodeRepository.findByBarcode("8008490000021"))
		.extracting(Barcode::getBarcode)
		.isEqualTo("8008490000021");
	}
	
}