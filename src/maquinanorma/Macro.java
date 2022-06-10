package maquinanorma;

/**
 *
 * @authors 
 *      Marina Zeni          RA:102011800
 *      Rafael Damacena      RA:102011966
 *         
**/

public class Macro 
{
    private final int TF=1000;
    private Registrador[] registradores;
    
    public Macro(char reg, int sinal, int valor) 
    {
        if (valor<0)
            valor=0;
        registradores = new Registrador[TF];
        for (int i=0; i<TF;i++)
            registradores[i] = new Registrador((char)0, 0);
        registradores[reg].valor = valor;
        registradores[reg].sinal = sinal;
        registradores[reg].util = true;
    }
    
    public void IniciaRegs(char[] Regs, int TL)
    {
        int i = 0;
        while(i < TL)
            this.registradores[Regs[i++]].util = true;
    }
    
    public void VisualizarRegs()
    {
        int i = 0, iv = 0;
        System.out.println("\n==> REGISTRADORES:");
        while(i<TF)
        {
            if(registradores[i].getUtil())
            {
                if(iv % 2 == 0 && iv != 0)
                {
                    System.out.print(" [" + (char)i + "] = " + getReg((char)i).toString() + " ");
                    System.out.print("\n");
                }    
                else
                    System.out.print(" [" + (char)i + "] = " +getReg((char)i).toString() + " ");
                iv++;
            }
            i++;
        }
    }
    
    public boolean zero(char reg)
    {
        return registradores[reg].valor==0;
    }
    
    public void add(char reg)
    {
        if(registradores[reg].sinal == 0)
            registradores[reg].valor++;
        else
        {
            registradores[reg].valor--;
            if(zero(reg))
                registradores[reg].sinal--;
        }
    }
    
    public void sub(char reg)
    {
        if(registradores[reg].sinal == 0 && registradores[reg].valor>0)
            registradores[reg].valor--;
        else
        {
            if(registradores[reg].valor==0)
                registradores[reg].sinal = 1;
            registradores[reg].valor++;
        }       
    }
    
    //--------MACROS----------
    public boolean positivo(char reg)
    {
        return registradores[reg].sinal==0;
    }
    
    public void AtribuirZero(char reg)
    {
        if(registradores[reg].sinal==0)
            while(!zero(reg))
                sub(reg);
        else
        {
            while(!zero(reg))
                add(reg);
            registradores[reg].sinal=0;
        }
    }
    
    public void AtribuirN(char reg, int n)
    {
        AtribuirZero(reg);
        if(n>=0)
        {
            registradores[reg].sinal=0;
            while(n>0)
            {
                add(reg);
                n--;
            }
        }
        else
        {
            registradores[reg].sinal=1;
            while(n<0)
            {
                sub(reg);
                n++;
            }
        }
    }
    
    public void SomarRegistradores(char regA, char regB)
    {
        if(positivo(regB))
        {
            while(!zero(regB))
            {
                add(regA);
                sub(regB);
            }
        }
        else if(!positivo(regB))
        {
            while(!zero(regB))
            {
                sub(regA);
                add(regB);
            }
        }
    }
    
    public void SomarRegistradoresPreservando(char regA, char regB)
    {
        boolean p=positivo(regB);
        AtribuirZero('c');
        while(!zero(regB))
        {
            add('c');
            if(p)
            {
                add(regA); 
                sub(regB);
            }
            else
            {
                sub(regA);
                add(regB);
            }
        }
        if(p)
            while(!zero('c'))
            {
                add(regB);
                sub('c');
            }  
        else
            while(!zero('c'))
            {
                sub(regB);
                sub('c');
            }
    }
     
    public void AtribuirReg(char regA, char regB)
    {
        AtribuirZero(regA);
        SomarRegistradoresPreservando(regA, regB);
    }
    
    public void SubtrairRegistradores(char regA, char regB)
    {
         if(positivo(regB))
            while(!zero(regB))
            {
                sub(regA);
                sub(regB);
            }
        else
            while(!zero(regB))
            {
                add(regA);
                add(regB);
            }
    }
    
    public void SubtrairRegistradoresPreservando(char regA, char regB)
    {
        AtribuirZero('1');
        while(!zero(regB)){
            if(positivo(regB)){
                sub(regA); 
                sub(regB);
                add('1');
            }else{
                add(regA);
                add(regB);
                sub('1');
            }
        }
        AtribuirReg(regB, '1');
    }
    
    public boolean menor(char regA, char regB) //Utiliza reg '2' como apoio
    { 
        AtribuirReg('2', regA);
        SubtrairRegistradoresPreservando('2', regB);
        if(positivo(regA) && positivo(regB))
            return !positivo('2'); //se A>0 e B>0 e A-B < 0 => A<B
        if(positivo(regA)) //se A>0 e e B<0;
            return false;
        if(positivo(regB))
            return true;
        //se A<0 e B<0 e abs(A) > abs(B)
        return registradores[regA].valor > registradores[regB].valor;
    }
    
    public boolean MenorIgual(char regA, char regB)
    {
        return menor(regA, regB) || registradores[regA].valor == registradores[regB].valor;
    }
    
    public boolean maior(char regA, char regB)
    {
        return !MenorIgual(regA, regB);
    }

    public boolean MaiorIgual(char regA, char regB)
    {
        return !menor(regA, regB);
    }

    public void MultiplicarRegs(char regA, char regB)
    {
        int mag = (!positivo(regA) && !positivo(regB)) || (positivo(regA) && positivo(regB))?0:1;
        AtribuirReg('1', regA);
        AtribuirZero(regA);
        while(!zero('1'))
        {
            SomarRegistradoresPreservando(regA, regB);
            if(positivo('1'))
                sub('1');
            else
                add('1');
        }
        registradores[regA].sinal = mag;
    }
    
    public void DividirRegs(char regA, char regB, char regRes)
    {
        if(!zero(regB))
        {
            AtribuirReg('d', regA);
            AtribuirZero(regRes);
            if((positivo(regA) && positivo(regB)))
                while(!menor('d', regB))
                {
                    add(regRes);
                    SubtrairRegistradoresPreservando('d', regB);
                }
            else if(!positivo(regA) && !positivo(regB))
                while(menor('d', regB))
                {
                    add(regRes);
                    SubtrairRegistradoresPreservando('d', regB);
                }
            else
            {
                if(positivo(regA))
                {
                    AtribuirN('z', -1); MultiplicarRegs('z', regB); // Z:= B*-1
                    while(!menor('d', 'z'))
                    {
                        add(regRes);
                        SubtrairRegistradoresPreservando('d', 'z');
                    }
                }
                else
                {
                    AtribuirN('z', -1); MultiplicarRegs('z', regB); // Z:= B*-1
                    while(menor('d', 'z'))
                    {
                        add(regRes);
                        SubtrairRegistradoresPreservando('d', 'z');
                    }
                }
                registradores[regRes].sinal=1;
            } 
        }
        else 
            AtribuirN(regRes, 0);
    }
    
    public void resto(char regA, char regB, char regRes)
    {
        DividirRegs(regA, regB, regRes);
        AtribuirReg(regRes, 'd');
    }
    
    public void ElevarReg(char reg, int n)
    {
        if(n>0)
        {
            AtribuirN('z', n);
            sub('z');
            AtribuirReg('Y', reg);
            while(!zero('z'))
            {
                MultiplicarRegs(reg, 'Y');
                sub('z');
            }
        }
    }
    
    public boolean primo(char reg)
    {
        AtribuirN('z',2);
        if(positivo(reg) && MaiorIgual(reg, 'z'))
        {
            while(menor('z',reg))
            {
                resto(reg, 'z', '0');
                if(zero('0'))
                    return false;
                add('z');
            }
            return true;
        }
        System.out.println("a");
        return false;
    }
    
    public void fatorial(char reg, char res)
    {
        if(positivo(reg))
        {
            AtribuirReg('z', reg);
            AtribuirN(res, 1);
            while(!zero('z')){
                MultiplicarRegs(res, 'z');
                sub('z');
            }
        }
    }
    
    public Registrador getReg(char reg)
    {
        return registradores[reg];
    }
}

class Registrador
{
    public int sinal; //1 - negativo | 0 - positivo
    public int valor;
    public boolean util;

    public Registrador(char sinal, int valor) 
    {
        this.sinal =  sinal;
        this.valor = valor;
        this.util = false;
    }

    public int getSinal()
    {
        return sinal;
    }
    public void setSinal(int sinal) 
    {
        this.sinal = sinal;
    }

    public int getValor() 
    {
        return valor;
    }
    public void setValor(int valor) 
    {
        this.valor = valor;
    }

    public boolean getUtil() 
    {
        return util;
    }
    public void setUtil(boolean valor) 
    {
        this.util = valor;
    }

    @Override
    public String toString() 
    {
        if(sinal==1)
            return "-"+valor;
        return ""+valor;
    } 
}
