package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class BeerRestControllerIT extends BaseIT {

    @Test
    public void deleteBeerCredInUrl() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/ea1f529f-e069-4279-84b6-887b1d0d0d15")
                        .param("apiKey", "spring").param("apiSecret", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteBeerCredInUrlBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/ea1f529f-e069-4279-84b6-887b1d0d0d15")
                        .param("apiKey", "spring").header("apiSecret", "guru123"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteBeerBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/ea1f529f-e069-4279-84b6-887b1d0d0d15")
                        .header("Api-Key", "spring").header("Api-Secret", "guruXXXX"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/ea1f529f-e069-4279-84b6-887b1d0d0d15")
                        .header("Api-Key", "spring").header("Api-Secret", "guru"))
                .andExpect(status().isOk());
    }

    @Test
    public void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer/"))
                .andExpect(status().isOk());
    }

    @Test
    public void findBeersById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/ea1f529f-e069-4279-84b6-887b1d0d0d15"))
                .andExpect(status().isOk());
    }

    @Test
    public void findBeersByUpcs() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/0631234200036"))
                .andExpect(status().isOk());
    }

}
