import com.avaliacaoimoveis.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;
/*professora estamos concluindo esse service, pois é feito por outro membro do  grupo do P3*/
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public <UserDetails> UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new SecurityProperties.User(
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getAuthorities()
        );
    }
}