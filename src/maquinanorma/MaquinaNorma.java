package maquinanorma;

/**
 *
 * @authors 
 *      Marina Zeni          RA:102011800
 *      Rafael Damacena      RA:102011966
 *         
**/

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaquinaNorma 
{
    private Macro MC;
    private Instrucao Programa[];
    private int TL, indiceI;

    public MaquinaNorma() 
    {
        IniciarMaquina('A', 0, 1);
        Programa = new Instrucao[100];
        TL = 0;
    }
    
    public void IniciarMaquina(char reg, int mag, int valor)
    {
        MC = new Macro(reg, mag, valor);
    }
    
    private void JMP0(char reg, int LinhaDest){
        if(!MC.zero(reg))
        {
            this.indiceI++;
            System.out.println("Estou indo para a linha: " + this.indiceI);
        } 
        else
        {
            System.out.println("Estou indo para a linha: " + LinhaDest);
            this.indiceI = LinhaDest;
        }
    }
    
    private void JMPmenor(char reg1, char reg2, int LinhaDest)
    {
        if(!MC.menor(reg1, reg2))
        {
            this.indiceI++;
            System.out.println("Estou indo para a linha: " + this.indiceI);   
        }
        else
        {
            System.out.println("Estou indo para a linha: " + LinhaDest);
            this.indiceI = LinhaDest;
        } 
    }
    
    
    private void JMPmaior(char reg1, char reg2, int LinhaDest)
    {
        if(!MC.maior(reg1, reg2))
        {
            this.indiceI++;
            System.out.println("Estou indo para a linha: " + this.indiceI);   
        }
        else
        {
            System.out.println("Estou indo para a linha: " + LinhaDest);
            this.indiceI = LinhaDest;
        } 
    }
    
    
    private void JMPmenorI(char reg1, char reg2, int LinhaDest)
    {
        if(!MC.MenorIgual(reg1, reg2))
        {
            this.indiceI++;
            System.out.println("Estou indo para a linha: " + this.indiceI);   
        }
        else{
            System.out.println("Estou indo para a linha: " + LinhaDest);
            this.indiceI = LinhaDest;
        } 
    }
    
    
    private void JMPmaiorI(char reg1, char reg2, int LinhaDest)
    {
        if(!MC.MaiorIgual(reg1, reg2))
        {
            this.indiceI++;
            System.out.println("Estou indo para a linha: " + this.indiceI);   
        }
        else
        {
            System.out.println("Estou indo para a linha: " + LinhaDest);
            this.indiceI = LinhaDest;
        } 
    }
    
    private void JMPprimo(char reg,int LinhaDest)
    {
        boolean verifica = MC.primo(reg);
        if(!verifica){
            this.indiceI++;
            System.out.println("[" + verifica + "]Estou indo para a linha: " + this.indiceI);   
        }
        else{
            System.out.println("[" + verifica + "]Estou indo para a linha: " + LinhaDest);
            this.indiceI = LinhaDest;
        } 
    }
    
    public void GOTO(int indice)
    {
        System.out.println("Estou indo para a linha: " + indice);
        this.indiceI = indice;
    }
    
    private void InterpretaInstrucao(String comando, String param1, String param2, String param3)
    {
        switch (comando){
            //Comandos padrão
            case "JMP0":    
                JMP0(param1.charAt(0),Integer.parseInt(param2));
                break;
            case "INC":
                MC.add(param1.charAt(0));
                indiceI++;
                break;
            case "DEC":
                MC.sub(param1.charAt(0));
                indiceI++;
                break;
            case "GOTO":
                GOTO(Integer.parseInt(param1));
                break;
            case "HALT": 
                System.out.println("Programa finalizado...");  
                indiceI++;
                break;   

            //Macros
            case "atribuirZero": 
                MC.AtribuirZero(param1.charAt(0));
                indiceI++;
                break;   
            case "atribuirN": 
                MC.AtribuirN(param1.charAt(0), Integer.parseInt(param2));
                indiceI++;
                break; 
            case "somar": 
                MC.SomarRegistradores(param1.charAt(0), param2.charAt(0));
                indiceI++;
                break; 
            case "somarPreservando": 
                MC.SomarRegistradoresPreservando(param1.charAt(0), param2.charAt(0));
                indiceI++;
                break; 
            case "atribuirReg": 
                MC.AtribuirReg(param1.charAt(0), param2.charAt(0));
                indiceI++;
                break; 
            case "subtrair": 
                MC.SubtrairRegistradores(param1.charAt(0), param2.charAt(0));
                indiceI++;
                break; 
            case "subtrairPreservando": 
                MC.SubtrairRegistradoresPreservando(param1.charAt(0), param2.charAt(0));
                indiceI++;
                break; 
            case "multiplicarRegs": 
                MC.MultiplicarRegs(param1.charAt(0), param2.charAt(0));
                indiceI++;
                break; 
            case "dividirRegs": 
                MC.DividirRegs(param1.charAt(0), param2.charAt(0), param3.charAt(0));
                indiceI++;
                break; 
            case "resto": 
                MC.resto(param1.charAt(0), param2.charAt(0), param3.charAt(0));
                System.out.println(param3.charAt(0));
                indiceI++;
                break; 
            case "elevarReg": 
                MC.ElevarReg(param1.charAt(0), Integer.parseInt(param2));
                indiceI++;
                break;
            case "fatorial": 
                MC.fatorial(param1.charAt(0), param2.charAt(0));
                indiceI++;
                break; 
            case "JMPmenor": 
                JMPmenor(param1.charAt(0), param2.charAt(0), Integer.parseInt(param3));
                break; 
            case "JMPmaior": 
                JMPmaior(param1.charAt(0), param2.charAt(0), Integer.parseInt(param3));
                break; 
            case "JMPmaiorI": 
                JMPmaiorI(param1.charAt(0), param2.charAt(0), Integer.parseInt(param3));
                break; 
            case "JMPmenorI": 
                JMPmenorI(param1.charAt(0), param2.charAt(0), Integer.parseInt(param3));
                break; 
            case "JMPprimo": 
                JMPprimo(param1.charAt(0), Integer.parseInt(param2));
                break;
        }
    }
    
    public void Interpretar()
    {
        Scanner sc = new Scanner(System.in);
        List<String> ProgramaLido = new ArrayList();
        String leitura="", instrucao[], comando, parametros, AuxParam[] = {"","",""}; 
        char[] VetReg = new char[30];
        int i, tam=0, NumLinha;

        System.out.println("1: JMP0(REG,X)\n" +
                           "2: DEC(REG)\n" +
                           "3: INC(REG)\n" +
                           "4: GOTO(INSTR)\n" +
                           "5: HALT\n\n");

        for (i=1; !(leitura.toUpperCase()).contains("HALT"); i++) //Le as instruções uma a uma até aparecer um HALT
        {
            System.out.print(i+": "); 
            leitura = sc.nextLine();
            ProgramaLido.add(i+":"+leitura.toUpperCase());
        }
        ProgramaLido.add(i+":"+leitura.toUpperCase());

        for(i=0; i<ProgramaLido.size(); i++)
        {
            instrucao = ProgramaLido.get(i).split(":"); //Separa o número da linha da instrução
            NumLinha = Integer.parseInt(instrucao[0]); //Recebe o número da linha
            if (!(instrucao[1].contains("HALT")))
            {
                comando = instrucao[1].substring(0, instrucao[1].indexOf("(")); //Recebe o comando
                parametros = instrucao[1].substring(instrucao[1].indexOf("(")+1, instrucao[1].indexOf(")")); //Recebe os parametros
                if(parametros.contains(","))
                {
                    AuxParam = parametros.split(","); //Separa o resgistrador do valor

                    if(AuxParam[0].charAt(0)>='A' && AuxParam[0].charAt(0)<='Z' )
                        VetReg[tam++] = AuxParam[0].charAt(0);
                    if(AuxParam.length>1 && AuxParam[1].charAt(0)>='A' && AuxParam[1].charAt(0)<='Z')
                        VetReg[tam++] = AuxParam[1].charAt(0);
                    if(AuxParam.length>2 && AuxParam[2].charAt(0)>='A' && AuxParam[2].charAt(0)<='Z')
                        VetReg[tam++] = AuxParam[2].charAt(0);
                    Programa[NumLinha] = new Instrucao(NumLinha, AuxParam[0], (AuxParam.length>1)?AuxParam[1]:null, (AuxParam.length>2)?AuxParam[2]:null, comando);
                }
                else
                {
                    if(parametros.charAt(0)>='A' && parametros.charAt(0)<='Z')
                        VetReg[tam++] = parametros.charAt(0);
                    Programa[NumLinha] = new Instrucao(NumLinha, AuxParam[0], null, null, comando);
                }
            }
            TL++;
        }
        sc.close();
        MC.IniciaRegs(VetReg, tam);
        executar();
    }
    
    public void executar()
    {
        this.indiceI = 1;
        
        while(this.indiceI < this.TL + 1){     
            MC.VisualizarRegs();
            System.out.println("\nInstrução: " + Programa[indiceI].toString());

            InterpretaInstrucao(Programa[indiceI].getFuncao(), Programa[indiceI].getParam1() , Programa[indiceI].getParam2(), Programa[indiceI].getParam3());
            System.out.println("------------------------------------");
        }
    }
}


class Instrucao
{
    private int linha;
    private String param1, param2, param3;
    private String funcao;

    public Instrucao(int linha, String param1, String param2, String param3, String funcao) 
    {
        this.linha = linha;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.funcao = funcao;
    }

    public int getLinha() 
    {
        return linha;
    }
    public void setLinha(int linha) 
    {
        this.linha = linha;
    }

    public String getParam1() 
    {
        return param1;
    }
    public void setParam1(String param1) 
    {
        this.param1 = param1;
    }

    public String getParam2() 
    {
        return param2;
    }
    public void setParam2(String param2) 
    {
        this.param2 = param2;
    }

    public String getParam3() 
    {
        return param3;
    }
    public void setParam3(String param3) 
    {
        this.param3 = param3;
    }

    public String getFuncao() 
    {
        return funcao;
    }
    public void setFuncao(String funcao) 
    {
        this.funcao = funcao;
    }

    @Override
    public String toString() 
    {
        return funcao + "(" + ((param1 == null) ? "" : param1) + ((param2 == null) ? "" : "," + param2) + ((param3 == null) ? "" : "," + param3) +")";
    }
}
