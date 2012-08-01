package br.com.processboss.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.processboss.core.model.User;
import br.com.processboss.core.service.IUserService;

//@Controller
//@Scope("session")
@SessionScoped
@ManagedBean
public class UserBean extends _Bean {

	@Autowired
	private IUserService userService;
	
	private User entity;
	private List<User> entities;
	
	/* CONSTRUTORES */
	public UserBean() {
		mock();
	}
	
	//TODO retirar quando acertar problema com Spring
	private void mock(){
		entities = new ArrayList<User>();
		entities.add(new User(1L, "Marco Paulo", "mollivier", "123456", true));
		entities.add(new User(2L, "Felipe Ribeiro", "fribeiro", "123456", true));
		entities.add(new User(3L, "Frederico Novaes", "fnovaes", "123456", false));
		entities.add(new User(4L, "Erico Torres", "etorres", "123456", false));
		setEntities(entities);
	}
	
	/* GETS and SETS */
	public User getEntity() {
		return entity;
	}
	public void setEntity(User entity) {
		this.entity = entity;
	}
	
	public List<User> getEntities() {
		return entities;
	}

	public void setEntities(List<User> entities) {
		this.entities = entities;
	}

	/* METODOS */
	public List<User> getAllEntities(){
//		System.out.println("testando");
//		@SuppressWarnings("unused")
//		return getEntities();
		return userService.listAll();
	}
	
	public String updateEntity(){
		entity = (User)getJsfParam("entity");
		return "updateUser";
	}
	
	public String newEntity(){
		entity = new User();
		return "newUser";
	}
	
	public String cancel(){
		return "index"; 
	}
	
	public String saveOrUpdate(){
		if(entity != null){
			userService.saveOrUpdate(entity);
//			entities.add(entity);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES, "Medico inserido/alterado com suscesso"));
			return "index";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível salvar/alterar", null));
			return null;
		}
	}
	
	public void captureUser(ActionEvent action){
		entity = (User)getJsfParam("entity");
	}
	
	public String delete(){
		if(entity != null){
			userService.delete(entity);
//			entities.remove(entity);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.FACES_MESSAGES, "Medico excluído com sucesso"));
			return "index";
		}else{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Não foi possível excluir", null));
			return null;
		}
	}
	
}
