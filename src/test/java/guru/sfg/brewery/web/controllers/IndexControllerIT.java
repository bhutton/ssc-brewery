package guru.sfg.brewery.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class IndexControllerIT extends BaseIT {

    @Test
    public void testGetIndexSlash() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testWebJars() throws Exception {
        mockMvc.perform(get("/webjars/jquery/3.3.0/jquery.min.js"))
                .andExpect(status().isOk());
    }

    @Test
    public void testResources() throws Exception {
        mockMvc.perform(get("/resources/fonts/montserrat-webfont.woff"))
                .andExpect(status().isOk());
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk());
    }
}
