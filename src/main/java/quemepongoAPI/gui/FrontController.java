package quemepongoAPI.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

//@CrossOrigin(origins = { "http://localhost:3000"})
@Controller
//@RequestMapping("/gui")
class FrontController {

    @GetMapping("/login")
    public String loginGui(@RequestParam(name="logoutNow",required = false,defaultValue = "")String logout, final Model model) {
       model.addAttribute("logoutNow", logout);
       return "login";
    }

    @GetMapping("/gui/main")
    public String mainWindow(@RequestParam(name="authToken",required = true,defaultValue = "")String authToken, @RequestParam(name="name",required = false,defaultValue = "")String name, final Model model) {
        model.addAttribute("authToken",authToken);
        model.addAttribute("name",name);
        //addToLoggedUsers(authToken);
        return "mainWindow";
    }

    @GetMapping("/gui/prenda")
    public String guiPrenda(@RequestParam(name="authToken",required = true,defaultValue = "")String authToken, final Model model) {
        model.addAttribute("authToken",authToken);
        return "agregarPrenda";
    }

    @GetMapping("/gui/evento")
    public String guiEvento(@RequestParam(name="authToken",required = true,defaultValue = "")String authToken, final Model model) {
        model.addAttribute("authToken",authToken);
        return "crearEvento";
    }

    @GetMapping("/gui/guardarropas")
    public String guiGuardarropas(@RequestParam(name="authToken",required = true,defaultValue = "")String authToken, final Model model) {
        model.addAttribute("authToken",authToken);
        return "crearGuardarropas";
    }

    @GetMapping("/gui/atuendo")
    public String guiAtuendo(@RequestParam(name="authToken",required = true,defaultValue = "")String authToken, final Model model) {
        model.addAttribute("authToken",authToken);
        return "pedirAtuendo";
    }

    @GetMapping("/gui/verGuardarropas")
    public String guiVerGuardarropas(@RequestParam(name="authToken",required = true,defaultValue = "")String authToken, final Model model) {
        model.addAttribute("authToken",authToken);
        return "verGuardarropas";
    }

    @GetMapping("/gui/verEventos")
    public String guiVerEventos(@RequestParam(name="authToken",required = true,defaultValue = "")String authToken, final Model model) {
        model.addAttribute("authToken",authToken);
        return "verEventos";
    }

    @GetMapping(value = "/crearGuardarropas")
    public String crear(){
        return "{}";
    }


}