package com.avaliacaoimoveis.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

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
    private String perfil;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Supondo que você tenha um campo role ou perfil na classe Usuario
        // Se for apenas um único perfil:
        return Collections.singletonList(new SimpleGrantedAuthority(this.perfil));

        // OU, se você tiver vários perfis armazenados de alguma forma:
        // return this.perfis.stream()
        //     .map(perfil -> new SimpleGrantedAuthority(perfil))
        //     .collect(Collectors.toList());
    }
}