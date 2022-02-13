package com.AppRH.AppRH.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.models.Empresa;
import com.AppRH.AppRH.repository.EmpresaRepository;

@Controller
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository er;
	
	// CADASTRAR EMPRESA
	@RequestMapping(value = "/cadastrarEmpresa", method = RequestMethod.GET)
	public String form() {
		
		return "empresa/form-empresa";
		
	}
	
	@RequestMapping(value = "/cadastrarEmpresa", method = RequestMethod.POST)
	public String form(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarEmpresa";
			
		}
		
		er.save(empresa);
		attributes.addFlashAttribute("mensagem", "Empresa cadastrada com sucesso");
		return "redirect:/cadastrarEmpresa";
		
	}
	
	// LISTA EMPRESA
	@RequestMapping("/empresas")
	public ModelAndView listaEmpresa() {
		
		ModelAndView mv = new ModelAndView("empresa/lista-empresa");
		Iterable<Empresa> empresas = er.findAll();
		mv.addObject("empresas", empresas);
		return mv;
		
	}
	
	// DELETA EMPRESA
	@RequestMapping("/deletarEmpresa")
	public String deletarEmpresa(long codigo) {
		
		Empresa empresa = er.findByCodigo(codigo);
		er.delete(empresa);
		return "redirect:/empresas";
		
	}
	
	// EDITAR EMPRESA
	@RequestMapping("/editar-empresa")
	public ModelAndView editarEmpresa(long codigo) {
		
		Empresa empresa = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("empresa/update-empresa");
		mv.addObject("empresa", empresa);
		return mv;
		
	}
	
	// UPDATE EMPRESA
	@RequestMapping(value = "/editar-empresa", method = RequestMethod.POST)
	public String updateEmpresa(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
		
		er.save(empresa);
		attributes.addFlashAttribute("sucessos","Empresa alterada com sucesso");
		
		long codigoLong = empresa.getCodigo();
		String codigo = "" + codigoLong;
		return "redirect:/empresa/" + codigo;
		
	}

	// GET que mostra os detalhes da Empresa
	@RequestMapping("/empresa/{codigo}")
	public ModelAndView detalhesEmpresa(@PathVariable("codigo") long codigo) {

		Empresa empresa = er.findByCodigo(codigo);
		ModelAndView mv = new ModelAndView("empresa/detalhes-empresa");
		mv.addObject("empresa", empresa);

		return mv;
	}
	
}
