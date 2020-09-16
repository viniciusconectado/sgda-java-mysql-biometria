package util;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class Convert {
    public static String convertCFOPNFe(String cfop, String ufDestinatario, String ufEmitente) throws Exception {
        String strCfop = cfop;
        if (!ufDestinatario.trim().equalsIgnoreCase(ufEmitente.trim())) {
            if(cfop.equals("5405")){
                strCfop = "6403";
            }else{
                strCfop = "6" + cfop.substring(1, 4);
            }
        }

        return strCfop;
    }
    
    /**
     * Retorna ano Ano e mes sem o caractere /
     *
     * @param calendar
     * @return String
     * @throws Exception
     */
    public static String getHorario(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("HH:mm:ss");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }

    /**
     * Remove todos os espaços dentro de uma String
     *
     * @param texto
     * @return String
     * @throws Exception
     */
    public static String removeEspacosDentroPalavra(String texto) throws Exception {
        String[] b = texto.split(" ");
        String c = "";
        int i = 0;

        for (i = 0; i < b.length; i++) {
            c += b[i];
        }

        return c;
    }

    /**
     * Converte a string para uma string a ser colocada no xml de nota fiscal
     * eletrônica
     *
     * @param string
     * @param tamanhoString
     * @return String
     * @throws Exception
     */
    public static String convertStringToNFe(String string, int tamanhoString) throws Exception {
        if (string != null) {
            String passa = string;
            passa = passa.replaceAll("[ÂÀÁÄÃ]", "A");
            passa = passa.replaceAll("[âãàáä]", "a");
            passa = passa.replaceAll("[ÊÈÉË]", "E");
            passa = passa.replaceAll("[êèéë]", "e");
            passa = passa.replaceAll("ÎÍÌÏ", "I");
            passa = passa.replaceAll("îíìï", "i");
            passa = passa.replaceAll("[ÔÕÒÓÖ]", "O");
            passa = passa.replaceAll("[ôõòóö]", "o");
            passa = passa.replaceAll("[ÛÙÚÜ]", "U");
            passa = passa.replaceAll("[ûúùü]", "u");
            passa = passa.replaceAll("Ç", "C");
            passa = passa.replaceAll("ç", "c");
            passa = passa.replaceAll("[ýÿ]", "y");
            passa = passa.replaceAll("Ý", "Y");
            passa = passa.replaceAll("ñ", "n");
            passa = passa.replaceAll("Ñ", "N");
            passa = passa.replaceAll("&", "E");
            passa = passa.replaceAll("%", "");
            passa = passa.replaceAll("['\"]", "");
            passa = passa.replaceAll("[<>()\\{\\}]", "");
            passa = passa.replaceAll("['\\\\.,()|/]", "");
            passa = passa.replaceAll("[^!-ÿ]{1}[^ -ÿ]{0,}[^!-ÿ]{1}|[^!-ÿ]{1}", " ");

            if (passa.length() > tamanhoString) {
                passa = passa.substring(0, tamanhoString - 1);
            }

            return passa;
        } else {
            return "";
        }
    }

    public static Color converteRGB(String hex) {
        String sr = hex.substring(0, 2);
        String sg = hex.substring(2, 4);
        String sb = hex.substring(4, 6);

        int r = Integer.parseInt(sr, 16);
        int g = Integer.parseInt(sg, 16);
        int b = Integer.parseInt(sb, 16);

        Color color = new Color(r, g, b);

        return color;
    }

    public static String convertDateToFaturaNFe(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormatada = formataData.format(calendar.getTime());
        return dataFormatada;
    }

    public static Calendar convertDateInterfaceToDateCompletaDB(String dataInterface, String hora) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = dateFormat.parse(dataInterface + " " + hora);
        calendar.setTime(date);
        return calendar;
    }

    public static Calendar convertDateInterfaceToDateCompletaDB(String dataInterface) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = dateFormat.parse(dataInterface);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Formata o decimal em formato correto para documentos eletrônicos
     *
     * @param decimal
     * @return String
     */
    public static String convertDecimalByNFe(double decimal) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("#0.00");
        return df.format(decimal).replaceAll(",", ".");
    }

    /**
     * Realiza arredondamento de valor double
     *
     * @param valor
     * @return double
     * @throws Exception
     */
    public static double arredondamento(double valor) throws Exception {
        String valorArredondado = convertMoedaInterface(valor);
        return convertMoedaBanco(valorArredondado);
    }

    /**
     * Converte o NCM para XML NF-e, caso esteja com menos de 8 caracteres
     * completa com 0 a esquerda.
     *
     * @param ncm
     * @return String
     * @throws Exception
     */
    public static String convertNCMToNFe(String ncm) throws Exception {
        if(ncm != null){
            if(ncm.length() > 8){
                ncm = ncm.substring(0, 8);
            }else{
                for (int i = ncm.length(); i < 8; i++) {
                    ncm = "0" + ncm;
                }
            }
        }else{
            return "00000000";
        }
        return ncm;
    }

    /**
     * Convert datetime para Time Stamp
     *
     * @param calendar
     * @return Timestamp
     */
    public static java.sql.Timestamp getCurrentTimeStamp(Calendar calendar) {
        java.util.Date today = calendar.getTime();
        return new java.sql.Timestamp(today.getTime());
    }

    /**
     * Format Number database to interfae 1234 to 000001234
     *
     * @param numero
     * @param qtdCaractere
     * @return String
     * @throws Exception
     */
    public static String convertNumeroInterface(String numero, int qtdCaractere) throws Exception {
        if (numero == null || numero.length() == 0 || numero.equals("null")) {
            return "";
        }

        if (!numero.equals("")) {
            String retorno = "";
            retorno = numero;
            long i = 0;
            long temp = 0;

            i = qtdCaractere - retorno.length();

            while (temp < i) {
                retorno = "0" + retorno;
                temp = temp + 1;
            }
            return retorno;
        } else {
            return "";
        }
    }

    public static Integer convertINFOInteger(String info) throws Exception {
        Integer integer = 0;

        if (info != null) {
            try {
                integer = Integer.parseInt(info);
            } catch (Exception e) {
            }
        }

        return integer;
    }

    public static double convertINFODouble(String info) throws Exception {
        double valor = 0;

        if (info != null) {
            try {
                valor = Double.parseDouble(info);
            } catch (Exception e) {
            }
        }

        return valor;
    }

    /**
     * Retorna a string pronta para ser enviada para o banco de dados.
     *
     * @param string
     * @return String
     * @throws Exception
     */
    public static String convertStringToDatabase(String string) throws Exception {
        if (string != null) {
            if (string.trim().length() > 0) {
                return string.trim().toUpperCase();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Conversor um objeto Calendar em data para ser exibida ao usuário do
     * sistema
     *
     * @param calendar
     * @return String
     * @throws Exception
     */
    public static String convertDateCompletaToInterface(Calendar calendar) throws Exception {
        if (calendar != null) {
            SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String dataFormatada = formataData.format(calendar.getTime());
            return dataFormatada;
        } else {
            return "";
        }
    }

    public static Calendar convertDateInterfaceToDateDBWEB(String dataInterface) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = dateFormat.parse(dataInterface);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Gera data para NF-e
     *
     * @param calendar
     * @return
     * @throws java.lang.Exception
     */
    public static String getDataNFe(Calendar calendar) throws Exception {
        Locale brasil = new Locale("pt", "br");//portugues do brasil
        TimeZone timeZone = TimeZone.getTimeZone("UTC");

        Calendar calendarHora = new GregorianCalendar(timeZone, brasil);

        //calendarHora.add(Calendar.HOUR, -3);
//        if (configURLUpload.getDiferenciaHorasWeb() != 0) {
//            calendarHora.add(Calendar.HOUR, configURLUpload.getDiferenciaHorasWeb());
//        }
        String dataFormat = "";
        String horaFormat = "";

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hs = new SimpleDateFormat("HH:mm");

        dataFormat = dt.format(calendar.getTime());
        horaFormat = hs.format(calendarHora.getTime());

        return dataFormat + "T" + horaFormat + ":00";
    }

    public static String normalizar(String stringAcentuada) {
        stringAcentuada = Normalizer.normalize(stringAcentuada, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        return stringAcentuada;
    }

    /**
     * Convert a string para ser exibida em interface.
     *
     * @param string
     * @return String
     * @throws Exception
     */
    public static String convertStringToInterface(String string) throws Exception {
        if (string != null) {
            return convertStringToDatabase(string);
        } else {
            return "";
        }
    }

    /**
     * Conversor um objeto Calendar em data para ser exibida ao usuário do
     * sistema
     *
     * @param calendar
     * @param completo
     * @return String
     * @throws Exception
     */
    public static String convertDateToInterface(Calendar calendar, boolean completo) throws Exception {
        if (calendar != null) {
            SimpleDateFormat formataData = null;
            if (completo) {
                formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            } else {
                formataData = new SimpleDateFormat("dd/MM/yyyy");
            }
            return formataData.format(calendar.getTime());
        } else {
            return "";
        }
    }

    
    public static Calendar convertDateInterfaceToDateDatabase(String dataInterface) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = dateFormat.parse(dataInterface);
        calendar.setTime(date);
        return calendar;
    }
    
    
    public static String convertDateToInterface(java.sql.Timestamp date, boolean completo) throws Exception {
        java.util.Date dateUtil = date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateUtil);

        if (calendar != null) {
            SimpleDateFormat formataData = null;
            if (completo) {
                formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            } else {
                formataData = new SimpleDateFormat("dd/MM/yyyy");
            }
            return formataData.format(calendar.getTime());
        } else {
            return "";
        }
    }

    public static String convertDateToHorarioInterface(java.sql.Timestamp date) throws Exception {
        java.util.Date dateUtil = date;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateUtil);

        if (calendar != null) {
            SimpleDateFormat formataData = new SimpleDateFormat("HH:mm:ss");
            return formataData.format(calendar.getTime());
        } else {
            return "";
        }
    }

    /**
     * Conversor date interface to Calendar java.
     *
     * @param dataInterface
     * @return Calendar
     * @throws Exception
     */
    

    public static Calendar convertDateInterfaceCompletaToDateDatabase(String dataInterface) throws Exception {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = dateFormat.parse(dataInterface);
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Conversor date to date interface
     *
     * @param calendar
     * @return String
     * @throws Exception
     */
    public static String convertDateToInterface(Calendar calendar) throws Exception {
        if (calendar != null) {
            SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
            String dataFormatada = formataData.format(calendar.getTime());
            return dataFormatada;
        } else {
            return "";
        }
    }

    public static String convertDateToInterfaceSped(Calendar calendar) throws Exception {
        if (calendar != null) {
            SimpleDateFormat formataData = new SimpleDateFormat("ddMMyyyy");
            String dataFormatada = formataData.format(calendar.getTime());
            return dataFormatada;
        } else {
            return "";
        }
    }

    public static String convertDateToInterfaceWeb(Calendar calendar) throws Exception {
        if (calendar != null) {
            SimpleDateFormat formataData = new SimpleDateFormat("yyyy-MM-dd");
            String dataFormatada = formataData.format(calendar.getTime());
            return dataFormatada;
        } else {
            return "";
        }
    }

    /**
     * Retorna a identidade do usuário formatada para a interface
     *
     * @param identidade
     * @return String
     * @throws Exception
     */
    public static String convertIdentidadeByInterface(String identidade) throws Exception {
        String retorno = "";

        if (identidade != null) {
            if (identidade.length() == 11) {
                retorno = identidade.substring(0, 3) + "." + identidade.substring(3, 6) + "." + identidade.substring(6, 9) + "-" + identidade.substring(9, 11);
            } else if (identidade.length() == 14) {
                retorno = identidade.substring(0, 2) + "." + identidade.substring(2, 5) + "." + identidade.substring(5, 8) + "/" + identidade.substring(8, 12) + "-" + identidade.substring(12, 14);
            } else {
                retorno = identidade;
            }
        }

        return retorno;
    }

    public static String getCPFOculto(String identidade) throws Exception {
        String retorno = "";
        if (identidade != null) {
            if (identidade.length() == 11) {
                retorno = "***.***." + identidade.substring(6, 9) + "-" + identidade.substring(9, 11);
            }
        }
        return retorno;
    }

    /**
     * Prepara a identidade para ser armazenada no banco de dados.
     *
     * @param identidade
     * @return String
     * @throws Exception
     */
    public static String convertIdentidadeByDatabase(String identidade) throws Exception {
        identidade = identidade.replaceAll("\\.", "");
        identidade = identidade.replaceAll("/", "");
        identidade = identidade.replaceAll("-", "");
        return identidade.trim();
    }

    /**
     * Conversor money database to interface PT-BR
     *
     * @param valor
     * @return String
     * @throws Exception
     */
    public static String convertMoedaInterface(double valor) throws Exception {
        String valorStr = null;
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        valorStr = nf.format(valor);
        valorStr = valorStr.replace("R", "");
        valorStr = valorStr.replace("$", "");
        valorStr = valorStr.replace(" ", "");
        return valorStr.trim();
    }

    /**
     * Convert um valor double no contexto de quantidade em quantidade interface
     * humana.
     *
     * @param valor
     * @param casasDecimal
     * @return String
     * @throws Exception
     */
    public static String convertQuantidadeInterface(double valor, int casasDecimal) throws Exception {
        String valorReal = null;
        String valorDecimal = null;

        String vetor[] = String.valueOf(valor).split("\\.");
        valorReal = vetor[0];

        if (vetor.length > 1) {
            valorDecimal = vetor[1];

            if (valorDecimal.length() < casasDecimal) {

                int diferenca = casasDecimal - valorDecimal.length();
                for (int i = 0; i < diferenca; i++) {
                    valorDecimal = valorDecimal + "0";
                }

            } else {
                valorDecimal = valorDecimal.substring(0, casasDecimal);
            }
        } else {

            for (int i = 0; i < casasDecimal; i++) {
                valorDecimal = valorDecimal + "0";
            }
        }

        return valorReal.trim() + "," + valorDecimal.trim();
    }

    /**
     * Conversor money interface to database
     *
     * @param valorInterface
     * @return double
     * @throws Exception
     */
    public static double convertMoedaBanco(String valorInterface) throws Exception {
        if (valorInterface == null) {
            return 0;
        } else {
            if (valorInterface.trim().length() > 0) {
                String valor = valorInterface;
                valor = valor.replaceAll("R", "");
                valor = valor.replaceAll(" ", "");
                valor = valor.replaceAll("$", "");
                valor = valor.replaceAll("\\.", "");
                valor = valor.replaceAll(",", ".");

                valor = valor.replace("R", "");
                valor = valor.replace(" ", "");
                valor = valor.replace("$", "");
                valor = valor.replace("\\.", "");
                valor = valor.replace(",", ".");

                return Double.parseDouble(valor.trim());
            } else {
                return 0;
            }
        }
    }
}
