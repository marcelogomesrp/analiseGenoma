package org.analiseGenoma.servlet;

import java.io.IOException;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.analiseGenoma.model.User;
import org.analiseGenoma.service.UsuarioService;

@WebServlet("/images/*")
public class userImage  extends HttpServlet {
    
    @Inject
    private UsuarioService usuarioService;
//    private boolean temImagem(Usuario user){
//        if(user != null)
//            if(user.getImage() != null)
//                if(user.getImage().length > 0)
//                    return true;
//        return false;
//    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String imageId = String.valueOf(request.getPathInfo().substring(1)); 
        User user = usuarioService.buscarPorId(Long.valueOf(imageId));
        response.setContentType("image/gif");   
//        if(this.temImagem(user))
//            response.getOutputStream().write(user.getImage());
//        else{
//            File file = new File("/tmp/1.gif");
//            byte[] data = Files.readAllBytes(file.toPath());
//            response.getOutputStream().write(data);
//        }
    }
}
