package com.xantrix.webapp.UnitTest.RepositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import com.xantrix.webapp.Application;
import com.xantrix.webapp.articoli.Articoli;
import com.xantrix.webapp.articoli.ArticoliRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestConstructor;
import org.junit.jupiter.api.Test;


@ContextConfiguration(classes = Application.class)
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArticoliRepositoryTest
{
	private final ArticoliRepository articoliRepository;

	@Test
	public void TestfindByDescrizioneLike()
	{
		List<Articoli> items = articoliRepository.findByDescrizioneLike("ACQUA ULIVETO%");
		assertEquals(2, items.size());
	}
	
	@Test
	public void TestfindByDescrizioneLikePage()
	{
		List<Articoli> items = articoliRepository.findByDescrizioneLike("ACQUA%",PageRequest.of(0, 10));
		assertEquals(10, items.size());
	}

	
	@Test
	public void TestfindByCodArt() throws Exception
	{
		assertThat(articoliRepository.findByCodArt("002000301"))
				.extracting(Articoli::getDescrizione)
				.isEqualTo("ACQUA ULIVETO 15 LT");
				
	}
	

}
