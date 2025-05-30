//document.addEventListener('DOMContentLoaded', function() {
//    const cepInput = document.getElementById('cep');
//    const buscarCepBtn = document.getElementById('buscarCep');
//    const logradouroInput = document.getElementById('logradouro');
//    const bairroInput = document.getElementById('bairro');
//    const cidadeInput = document.getElementById('cidade');
//    const ufInput = document.getElementById('uf');
//
//    // Máscara para o CEP
//    cepInput.addEventListener('input', function(e) {
//        let value = e.target.value.replace(/\D/g, '');
//        if (value.length > 8) {
//            value = value.substring(0, 8);
//        }
//        e.target.value = value;
//    });
//
//    // Buscar CEP
//    buscarCepBtn.addEventListener('click', buscarCEP);
//    cepInput.addEventListener('blur', buscarCEP);
//
//    function buscarCEP() {
//        const cep = cepInput.value.replace(/\D/g, '');
//
//        if (cep.length !== 8) {
//            alert('CEP deve conter 8 dígitos');
//            return;
//        }
//
//        fetch(`/avaliacao-imoveis/api/imoveis/cep/${cep}`)
//            .then(response => {
//                if (!response.ok) {
//                    throw new Error('CEP não encontrado');
//                }
//                return response.json();
//            })
//            .then(endereco => {
//                logradouroInput.value = endereco.logradouro || '';
//                bairroInput.value = endereco.bairro
//            })
//    };
//})
document.addEventListener('DOMContentLoaded', () => {
  const cepInput       = document.getElementById('cep');
  const buscarCepBtn   = document.getElementById('buscarCep');
  const logradouroInput= document.getElementById('logradouro');
  const bairroInput    = document.getElementById('bairro');
  const cidadeInput    = document.getElementById('cidade');
  const ufInput        = document.getElementById('uf');

  // Garante que o botão não submeta o form
  buscarCepBtn.type = 'button';

  // Máscara básica pro CEP
  cepInput.addEventListener('input', e => {
    let v = e.target.value.replace(/\D/g, '');
    if (v.length > 8) v = v.slice(0, 8);
    e.target.value = v;
  });

  buscarCepBtn.addEventListener('click', buscarCEP);
  cepInput.addEventListener('blur', buscarCEP);

  async function buscarCEP(e) {
    e.preventDefault();

    const cep = cepInput.value.replace(/\D/g, '');
    if (cep.length !== 8) {
      alert('O CEP deve ter exatamente 8 dígitos.');
      return;
    }

    try {
      // Ajuste aqui pra URL incluir o contexto da sua app
      const response = await fetch(`/avaliacao-imoveis/api/ceps/${cep}`);

      if (!response.ok) {
        // se o status vier 404, 500 etc
        throw new Error('CEP não encontrado no servidor');
      }

      const data = await response.json();

      // se sua API seguir o padrão ViaCEP, pode vir { erro: true }
      if (data.erro) {
        throw new Error('CEP não existe');
      }

      // Preenche os campos (ViaCEP usa "localidade" pra cidade)
      logradouroInput.value = data.logradouro  || '';
      bairroInput.value     = data.bairro      || '';
      cidadeInput.value     = data.localidade  || '';
      ufInput.value         = data.uf          || '';
    }
    catch(err) {
      alert(err.message);
      // opcional: limpar campos
      logradouroInput.value = '';
      bairroInput.value     = '';
      cidadeInput.value     = '';
      ufInput.value         = '';
    }
  }
});
