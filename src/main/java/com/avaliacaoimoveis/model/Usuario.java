import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String email;

    @NotBlank
    private String senha;

    private boolean ativo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuarios_permissoes", joinColumns = @JoinColumn(name = "usuario_id"))
    @Column(name = "permissao")
    private Set<String> permissoes = new HashSet<>();
}