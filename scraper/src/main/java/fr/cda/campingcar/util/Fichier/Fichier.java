package fr.cda.campingcar.util.Fichier;

import java.io.IOException;

public interface Fichier {


        /*
        methode delecture de fichier
         */
        public void readFile() throws IOException;
        /*
        méthode ecriture de fichier
         */
        public void writeFile() throws IOException;

    }


