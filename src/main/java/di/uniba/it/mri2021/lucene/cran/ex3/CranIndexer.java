/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package di.uniba.it.mri2021.lucene.cran.ex3;

import com.google.gson.Gson;
import di.uniba.it.mri2021.lucene.cran.CranDocument;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author pierpaolo
 */
public class CranIndexer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            FSDirectory fsdir = FSDirectory.open(new File(args[1]).toPath());
            IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter writer = new IndexWriter(fsdir, iwc);
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            Gson gson = new Gson();
            while (reader.ready()) {
                CranDocument crandoc = gson.fromJson(reader.readLine(), CranDocument.class);
                Document doc = new Document();
                doc.add(new StringField("id", crandoc.getId(), Field.Store.YES));
                doc.add(new TextField("title", crandoc.getTitle(), Field.Store.NO));
                doc.add(new TextField("text", crandoc.getText(), Field.Store.NO));
                writer.addDocument(doc);
            }
            writer.close();
        }
    }
}
