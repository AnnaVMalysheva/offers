package com.example.offers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc(secure = false)
public class OffersApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void doOfferImport() throws Exception {
		File file = ResourceUtils.getFile(this.getClass().getResource("/test.xml"));
		String url = URLEncoder.encode(file.getAbsolutePath(), StandardCharsets.UTF_8.toString());
		mockMvc.perform(get("/jobLauncher?fileurl=" + url))
				.andExpect(status().isOk());
	}


}