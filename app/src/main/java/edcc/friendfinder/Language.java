package edcc.friendfinder;

class Language implements Comparable<Language>{

    private String[] languages = {"Arabic", "Chinese (Cantonese)", "Chinese (Mandarin)", "English",
            "French", "German", "Indonesian (Malay)", "Hindustani", "Japanese", "Korean", "Russian",
            "Spanish", "Vietnamese"};

    private String language;

    public Language(){

    }

    public Language(int incLanguage){
        language = languages[incLanguage];
    }

    String getLanguage(){
        return language;
    }

    String[] getLanguageList(){
        return languages;
    }

    @Override
    public int compareTo(Language o) {
        return 0;
    }
}
