package edcc.friendfinder;

class Language implements Comparable<Language>{

    private String[] languages = {"Arabic", "Chinese (Cantonese)", "Chinese (Mandarin)", "English",
            "French", "German", "Indonesian (Malay)", "Hindustani", "Japanese", "Korean", "Russian",
            "Spanish", "Vietnamese"};

    private String language;
    private int languageId;

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

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    @Override
    public int compareTo(Language o) {
        return 0;
    }
}
