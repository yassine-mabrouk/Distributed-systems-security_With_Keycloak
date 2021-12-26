package ma.enset.productsapp.web;

import ma.enset.productsapp.entities.Supplier;
import ma.enset.productsapp.repositories.ProductRepository;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.jetty.core.AbstractKeycloakJettyAuthenticator;
import org.keycloak.adapters.springsecurity.client.KeycloakRestTemplate;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ProductController{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KeycloakRestTemplate keycloakRestTemplate ;

    @GetMapping("/")
    public String index(){
        return "index";
    }
    @GetMapping("/products")
    public String products(Model model){
        model.addAttribute("products",productRepository.findAll());
        return "products";
    }

    // Methode interagir avec ms suppliers avec RestTemplate de keyClok
    @GetMapping("/suppliers")
    public String suppliers(Model model){
        PagedModel<Supplier> pageModel =keycloakRestTemplate.getForObject("http://localhost:8083/suppliers",PagedModel.class);
        model.addAttribute("suppliers",pageModel);
        return "suppliers";
    }
    @ExceptionHandler (Exception.class)
    public String errorHandler(Exception e ,Model model){
         model.addAttribute("error","Error probleme d'autorisation !!!");
         return "error";
    }


     @GetMapping("/jwt")
     @ResponseBody // pour retourner en format Json
     public Map<String,String> getJwt(HttpServletRequest request ){
         // get user
         KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
         // pricipal qui contient les donnes de user auth
         KeycloakPrincipal pricipal= (KeycloakPrincipal) token.getPrincipal();
      //   KeycloakSecurityContext stoke les info du user auth
         KeycloakSecurityContext context=pricipal.getKeycloakSecurityContext();
         Map<String,String> tokenInfo=new HashMap<String,String>();
         tokenInfo.put("access-token ", context.getIdTokenString());
         tokenInfo.put("Realm ",context.getRealm());
         return  tokenInfo;
     }

         /*
    // Methode interagir avec ms suppliers avec RestTemplate
    @GetMapping("/suppliers2")
    public String suppliers2(HttpServletRequest request){
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal= (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext context= principal.getKeycloakSecurityContext();
        // Creer une rest Templete
        RestTemplate restTemplate =new RestTemplate();
        // crer un header
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer "+context.getTokenString());
        HttpEntity httpEntity =new HttpEntity(httpHeaders);
       ResponseEntity<PageModel<Supplier>> response=restTemplate.exchange(
                "http://localhost:8083/suppliers", HttpMethod.POST,new ParameterizedTypeReference<Supplier>()
        )

        return "suppliers";
    }

      */
}
