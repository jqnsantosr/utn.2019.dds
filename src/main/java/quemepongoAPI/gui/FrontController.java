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

    @GetMapping("/")
    public String loginGui(final Model model) {
       //model.addAttribute("name","pepito");
       return "login";
    }

    //gui/main?name=asddsa
    @GetMapping("/gui/main")
    public String mainWindow(@RequestParam(name="name",required = false,defaultValue = "")String nombreParametro, final Model model) {
        model.addAttribute("nombre",nombreParametro);
        return "mainWindow";
    }

    @GetMapping("/gui/prenda")
    public String guiPrenda(@RequestParam(name="name",required = false,defaultValue = "")String nombreParametro, final Model model) {
        model.addAttribute("nombre",nombreParametro);
        return "agregarPrenda";
    }

    @GetMapping("/gui/evento")
    public String guiEvento(@RequestParam(name="name",required = false,defaultValue = "")String nombreParametro, final Model model) {
        model.addAttribute("nombre",nombreParametro);
        return "crearEvento";
    }

    @GetMapping("/gui/guardarropas")
    public String guiGuardarropas(@RequestParam(name="name",required = false,defaultValue = "")String nombreParametro, final Model model) {
        model.addAttribute("nombre",nombreParametro);
        return "crearGuardarropas";
    }

    @GetMapping("/gui/atuendo")
    public String guiAtuendo(@RequestParam(name="name",required = false,defaultValue = "")String nombreParametro, final Model model) {
        model.addAttribute("nombre",nombreParametro);
        return "pedirAtuendo";
    }

    @GetMapping("/gui/verGuardarropas")
    public String guiVerGuardarropas(@RequestParam(name="name",required = false,defaultValue = "")String nombreParametro, final Model model) {
        model.addAttribute("nombre",nombreParametro);
        return "verGuardarropas";
    }

    @GetMapping("/gui/verEventos")
    public String guiVerEventos(@RequestParam(name="name",required = false,defaultValue = "")String nombreParametro, final Model model) {
        model.addAttribute("nombre",nombreParametro);
        return "verEventos";
    }

    @GetMapping(value = "/crearGuardarropas")
    public String crear(){
        return "{}";
    }


}