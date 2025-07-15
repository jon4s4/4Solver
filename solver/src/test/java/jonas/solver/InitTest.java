package jonas.solver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class InitTest {
    
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("die Startseite ist unter / erreichbar, und zwar per redirect wegen spring security")
    void test_1() throws Exception{
        mvc.perform(get("/"))
        .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username="testuser", roles = {"USER", "ADMIN"})
    @DisplayName("Eingeloggte User erreichen ebenfalls / per 200")
    void test_2() throws Exception{
        mvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Nicht-eingeloggte user können nicht auf /main zugreifen und werden redirected")
    void test_3() throws Exception{
        mvc.perform(get("/main"))
        .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER", "ADMIN"})
    @DisplayName("Eingeloggte User erreichen /main")
    void test_4() throws Exception{
        mvc.perform(get("/main")).andExpect(status().isOk());
    }

}
