document.addEventListener('DOMContentLoaded', () => {
    // Manipulação do formulário de avaliação
    const form = document.getElementById('avaliacaoForm');

    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();

            try {
                const response = await fetch('/avaliacao-imoveis/api/avaliar', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        // Capturar dados do formulário
                        tipo: form.tipo.value,
                        metragem: parseFloat(form.metragem.value),
                        quartos: parseInt(form.quartos.value),
                        banheiros: parseInt(form.banheiros.value),
                        vagas: parseInt(form.vagas.value),
                        unidade: form.unidade.value,
                        endereco: {
                            cep: form.cep.value,
                            logradouro: form.logradouro.value,
                            numero: form.numero.value,
                            complemento: form.complemento.value,
                            bairro: form.bairro.value,
                            cidade: form.cidade.value,
                            uf: form.uf.value
                        },
                        proprietario: {
                            nome: form.nome.value,
                            cpf: form.cpf.value,
                            email: form.email.value,
                            telefone: form.telefone.value
                        }
                    })
                });
                const data = await response.json();

                window.location.href = data.redirectUrl; // Redireciona para a URL retornada pela API

                if (response.ok) {
                    const resultado = await response.json();
                    // Redirecionar para página de resultado ou mostrar modal
                    window.location.href = `/avaliacao-imoveis/resultado.html?id=${resultado.id}`;
                } else {
                    throw new Error('Erro na avaliação');
                }
            } catch (error) {
                console.error('Erro:', error);
                alert('Ocorreu um erro ao avaliar o imóvel');
            }
        });
    }
});