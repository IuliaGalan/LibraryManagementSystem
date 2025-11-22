// Pachetul în care se află repository-ul
package com.example.librarymanagementsystem.repository;

// Importă clasa care ajută Jackson să lucreze cu liste generice
import com.fasterxml.jackson.core.type.TypeReference;
// Importă clasa principală pentru citire/scriere JSON
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// Clasă generică ce implementează un repository bazat pe fișiere JSON
public class InFileRepository<T> implements RepositoryInterface<T> {

    // Map in-memory ce reține entitățile după ID, păstrează ordinea de inserare
    private final Map<String, T> data = new LinkedHashMap<>();

    // ObjectMapper pentru citirea și scrierea JSON
    private final ObjectMapper mapper = new ObjectMapper();

    // Calea către fișierul JSON unde se salvează datele
    private final Path filePath;

    // Tipul concret al listei de entități (List<T>) pentru Jackson
    private final TypeReference<List<T>> listTypeRef;

    // Constructorul primește calea fișierului și tipul listei și încarcă datele
    public InFileRepository(String filePath, TypeReference<List<T>> listTypeRef) {
        // Transformă calea din String în Path
        this.filePath = Path.of(filePath);
        // Salvează tipul listei
        this.listTypeRef = listTypeRef;
        // Încarcă automat datele din fișier la crearea repository-ului
        loadFromDisk();
    }

    // Încarcă datele din fișier într-un mod sincronizat (sigur pentru thread-uri)
    private synchronized void loadFromDisk() {
        try {
            // Transformă Path în File
            File f = filePath.toFile();

            // Creează directorul dacă nu există
            if (!f.getParentFile().exists()) f.getParentFile().mkdirs();

            // Creează fișierul cu listă goală dacă nu există
            if (!f.exists()) Files.writeString(filePath, "[]");

            // Citește lista de obiecte T din JSON
            List<T> list = mapper.readValue(f, listTypeRef);

            // Golește map-ul in-memory
            data.clear();

            // Parcurge lista și introduce fiecare obiect în map după ID
            for (T item : list) {
                data.put(extractId(item), item);
            }

        } catch (Exception e) {
            // Aruncă excepție clară dacă citirea eșuează
            throw new RuntimeException("Nu pot încărca din " + filePath + ": " + e.getMessage(), e);
        }
    }

    // Salvează toate datele din memorie în fișierul JSON
    private synchronized void saveToDisk() {
        try {
            // Scrie lista de entități în fișier într-un format JSON frumos indentat
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(filePath.toFile(), new ArrayList<>(data.values()));
        } catch (Exception e) {
            // Aruncă excepție dacă salvarea eșuează
            throw new RuntimeException("Nu pot salva în " + filePath + ": " + e.getMessage(), e);
        }
    }

    // Extrage ID-ul unui obiect T folosind reflecție (apel la getId())
    private String extractId(T item) {
        try {
            // Găsește metoda getId() și o apelează
            return (String) item.getClass().getMethod("getId").invoke(item);
        } catch (Exception e) {
            // Aruncă excepție dacă entitatea nu are getId()
            throw new RuntimeException("Entitatea nu are getId(): " + item.getClass().getSimpleName(), e);
        }
    }

    // Returnează toate entitățile sub formă de listă
    @Override
    public synchronized List<T> findAll() {
        // Returnează o listă nouă pentru a preveni modificări necontrolate
        return new ArrayList<>(data.values());
    }

    // Returnează entitatea cu ID-ul dat
    @Override
    public synchronized T findById(String id) {
        // Ia din map entitatea asociată ID-ului
        return data.get(id);
    }

    // Salvează (crează sau actualizează) o entitate după ID
    @Override
    public synchronized void save(String id, T entity) {
        // Verifică validitatea ID-ului
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID invalid");

        // Pune entitatea în map (suprascrie dacă exista deja)
        data.put(id, entity);

        // Salvează modificările în fișier
        saveToDisk();
    }

    // Șterge o entitate după ID
    @Override
    public synchronized void delete(String id) {
        // remove() întoarce entitatea ștearsă; dacă nu era nimic, întoarce null
        if (data.remove(id) != null)
            // Salvează doar dacă chiar s-a șters ceva
            saveToDisk();
    }
}
