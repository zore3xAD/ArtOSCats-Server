package artoscats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller()
@RequestMapping(path="/catsApi")
public class MainController {

    @Autowired
    private CatRepository catRepository;

    @PostMapping(path="/insert") // Map ONLY GET Requests
    public @ResponseBody String insertCat (@RequestParam String name
            , @RequestParam Integer age) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Cat cat = new Cat();
        cat.setName(name);
        cat.setAge(age);
        catRepository.save(cat);
        return "Inserted";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Cat> getAllCats() {
        // This returns a JSON or XML with the users
        return catRepository.findAll();
    }

    @GetMapping(path="/getCat")
    public @ResponseBody
    Optional<Cat> getCat(@RequestParam Long catId) {
        return catRepository.findById(catId);
    }

    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteCat(@RequestParam Long catId) {

        if(catRepository.existsById(catId)) {
            catRepository.deleteById(catId);
            return "Delete";
        } else {
            return "Not found";
        }
    }

    @PutMapping(path = "/update")
    public @ResponseBody String updateCat(@RequestParam Long catId, @RequestParam String name,
                                                 @RequestParam Integer age) {
        try {
            Cat cat = catRepository.findById(catId).get();
            cat.setName(name);
            cat.setAge(age);
            catRepository.save(cat);
        }
        catch (Exception ex) {
            return "Error updating the cat: " + ex.toString();
        }
        return "CAt successfully updated!";
    }
}
