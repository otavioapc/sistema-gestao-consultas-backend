package br.com.vestaplan.api.utils;

public class TextoUtils {

    // Remove espaços extras e deixa as iniciais em maiúsculo
    public static String higienizarNome(String nome) {
        if (nome == null || nome.isBlank()) return null;

        String textoLimpo = nome.trim().replaceAll("\\s+", " ");

        String[] palavras = textoLimpo.toLowerCase().split(" ");
        StringBuilder nomeFormatado = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                nomeFormatado.append(Character.toUpperCase(palavra.charAt(0)))
                        .append(palavra.substring(1))
                        .append(" ");
            }
        }

        return nomeFormatado.toString().trim();
    }

    // Remove pontos, traços, parênteses
    public static String limparMascaras(String texto) {
        if (texto == null) return null;
        return texto.replaceAll("\\D", ""); // \D significa: "tudo que NÃO for dígito"
    }

}
