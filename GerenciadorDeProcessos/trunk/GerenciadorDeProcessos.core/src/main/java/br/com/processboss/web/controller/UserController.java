package br.com.processboss.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import br.com.processboss.core.model.User;
import br.com.processboss.core.service.IUserService;

@ManagedBean(name="userController")
@SessionScoped
public class UserController extends _Bean implements Serializable {

	private static final long serialVersionUID = 7316183441445886925L;

	@ManagedProperty(name="userService", value="#{userService}")
	private IUserService userService;
	
	private User entity;
	private List<User> entities;
	
	public UserController() {}
	
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

	public List<User> getAllEntities(){
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
			addMessage( new FacesMessage(FacesMessage.FACES_MESSAGES, "Usuario inserido/alterado com suscesso"));
			return "index";
		}else{
			addMessage( new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nao foi possivel salvar/alterar", null));
			return null;
		}
	}
	
	public void captureUser(ActionEvent action){
		entity = (User)getJsfParam("entity");
	}
	
	public String delete(){
		if(entity != null){
			userService.delete(entity);
			addMessage(new FacesMessage(FacesMessage.FACES_MESSAGES, "Usuario removido com sucesso"));
			return "index";
		}else{
			addMessage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nao foi possivel excluir", null));
			return null;
		}
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
