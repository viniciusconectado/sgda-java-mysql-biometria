package util;



import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class GenericCalendar {
    
    /**
     * Retorna ano Ano e mes sem o caractere /
     * @param calendar
     * @return String
     * @throws Exception 
     */
    public static String getYYYYMMM(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("yyyyMMdd");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }
    
    public static String getNumeroAleatorioBaseData(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("ddHHmmss");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }
    
    public static Calendar somaDias(Calendar calendar, int dias) throws Exception{
        Calendar data = calendar;
        data.add(data.DAY_OF_MONTH, + dias);
        return data;
    }
    
    public static Calendar subtrairDias(Calendar calendar, int dias) throws Exception{
        Calendar data = calendar;
        data.add(data.DAY_OF_MONTH, - dias);
        return data;
    }
    
    /**
     * Gera o primeiro dia do mes de um calendar
     * @param calendar
     * @return Calendar
     * @throws Exception 
     */
    public static Calendar getPrimeiroDiaMes(Calendar calendar) throws Exception {
        String datastring = Convert.convertDateToInterface(calendar);
        datastring = "01/" + datastring.substring(3, 10);
        return Convert.convertDateInterfaceToDateCompletaDB(datastring, "00:00:00");
    }
    
    public static Calendar getUltimoDiaDoMes(Calendar calendar) {
        //muda a data da variável para o último dia do mês
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        return calendar;
    }
    
    /**
     * Retorna a quantidade de dias entre duas datas
     * @param datainicial
     * @param dataFinal
     * @return int
     * @throws Exception 
     */
    public static int getQtdDiasEntreDatas(Calendar datainicial, Calendar dataFinal) throws Exception {
        int dias = 0;
        
        if(dataFinal.after(datainicial)){
            Date d1 = datainicial.getTime();
            Date d2 = dataFinal.getTime();
            long dt = (d2.getTime() - d1.getTime()) + 3600000;
            
            dias = (int) (dt / 86400000L);
        }
        
        return dias;
    }
    
    public static int getQtdDiasEntreDatasContagemBruta(Calendar datainicial, Calendar dataFinal) throws Exception {
        int dias = 0;
        
        if(dataFinal.after(datainicial)){
            Date d1 = datainicial.getTime();
            Date d2 = dataFinal.getTime();
            long dt = (d2.getTime() - d1.getTime()) + 3600000;
            
            dias = (int) (dt / 86400000L);
        }
        
        return dias + 1;
    }
    
    /**
     * Return calendar current of system
     *
     * @param completo
     * @return Calendar
     * @throws Exception
     */
    public static Calendar getCalendarCurrent(boolean completo) throws Exception {
        if(completo){
            return Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt", "BR"));
        }else{
            Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"), new Locale("pt", "BR"));
            String dataInterface = Convert.convertDateToInterface(c);
            c = Convert.convertDateInterfaceToDateDatabase(dataInterface);
            return c;
        }            
    }
    
    /**
     * Retorna ano Ano e mes sem o caractere /
     * @param calendar
     * @return String
     * @throws Exception 
     */
    public static String getYYYYMM(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("yyyyMM");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }
    
    public static String getYYYY(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("yyyy");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }
    
    public static String getMM(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("MM");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }
    
    public static String getDia(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("dd");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }
    
    /**
     * Retorna o nome em stirng para o dia
     * @param cal
     * @return String
     */
    public static String getNomeDia(Calendar cal) {
        return new DateFormatSymbols().getWeekdays()[cal.get(Calendar.DAY_OF_WEEK)];
    }
    
    public static String getMes(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("MM");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }
    
    public static String getAno(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("yyyy");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }
    
    public static String getYYMM(Calendar calendar) throws Exception {
        SimpleDateFormat formataData = new SimpleDateFormat("yyMM");
        String anoFormatado = formataData.format(calendar.getTime());
        return anoFormatado;
    }
}
