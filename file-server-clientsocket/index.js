const net = require('net');
const fs = require("fs");
const readline = require('readline');


function conectarETransferirServidor (porta, nomeArquivo = "placeholder.txt") {
    const client = net.createConnection({ port: porta }, () => {
        // 'connect' listener.
        console.log('Conectado ao servidor!');
        // client.write('world!\r\n');
    });
    
    client.on('connect', (data) => {
        console.log('\nIniciando transferência!');

        console.log(nomeArquivo || "placeholder.txt\n")
        client.write(`${nomeArquivo}\n` || "placeholder.txt\n");

        console.log('\nNome de arquivo enviado!');

        console.log('\nIniciando upload de arquivo!');
        fs.readFile(nomeArquivo , (err, data) =>{
            if(!err){
                client.write(data);
            }
            else {
                console.log('\nError: Arquivo não existe no sistema!\n\n');
                console.log(err)
            }
        });
        client.pipe(client);


        // client.end();
    });

    client.on('end', () => {
        console.log('\nSucesso: desconectado do servidor!');
    });
}

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

rl.question('Porta do servidor: ', function (porta) {
  rl.question('Nome do arquivo: ', function (nome) {
    conectarETransferirServidor(porta, nome)
    rl.close();
  });
});



