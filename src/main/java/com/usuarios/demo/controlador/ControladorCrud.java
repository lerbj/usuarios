
package com.usuarios.demo.controlador;

import com.usuarios.demo.modelo.Usuario;
import com.usuarios.demo.modelo.UsuarioCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
@RequestMapping("/crud")
public class ControladorCrud {

    @Autowired
    private UsuarioCrud uc;

    @RequestMapping(method = RequestMethod.GET)
    public String listaUsuarios(ModelMap mp){
        mp.put("usuarios", uc.findAll());
        return "crud/lista";
    }

    @RequestMapping(value="/nuevo", method=RequestMethod.GET)
    public String nuevo(ModelMap mp){
        mp.put("usuario", new Usuario());
        return "crud/nuevo";
    }

    @RequestMapping(value="/crear", method=RequestMethod.POST)
    public String crear(@Valid Usuario usuario,
                        BindingResult bindingResult, ModelMap mp){
        if(bindingResult.hasErrors()){
            return "/crud/nuevo";
        }else{
            uc.save(usuario);
            mp.put("usuario", usuario);
            return "crud/creado";
        }
    }


    @RequestMapping(value="/borrar/{id}", method=RequestMethod.GET)
    public String borrar(@PathVariable("id") long id, ModelMap mp){
            uc.delete(uc.findOne(id));
        mp.put("usuarios", uc.findAll());
        return "crud/lista";
    }

    @RequestMapping(value="/editar/{id}", method=RequestMethod.GET)
    public String editar(@PathVariable("id") long id, ModelMap mp){
        mp.put("usuario", uc.findOne(id));
        return "crud/editar";
    }

    @RequestMapping(value="/actualizar", method=RequestMethod.POST)
    public String actualizar(@Valid Usuario usuario, BindingResult bindingResult, ModelMap mp){
        if(bindingResult.hasErrors()){
            mp.put("usuarios", uc.findAll());
            return "crud/lista";
        }
        Usuario user = uc.findOne(usuario.getId());
        user.setNombre(usuario.getNombre());
        user.setPassword(usuario.getPassword());
        user.setEmail(usuario.getEmail());
        uc.save(user);
        mp.put("usuario", user);
        return "crud/actualizado";
    }

    @RequestMapping(value="/creado", method = RequestMethod.POST)
    public String creado(@RequestParam("usuario") Usuario usuario){
        return "/crud/creado";
    }




}