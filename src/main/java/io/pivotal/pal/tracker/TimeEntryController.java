package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntriesRepository;

    public TimeEntryController(TimeEntryRepository timeEntriesRepository) {
        this.timeEntriesRepository = timeEntriesRepository;
    }

    @PostMapping
    public ResponseEntity<TimeEntry> create(@RequestBody @NotNull TimeEntry entry) {
        return new ResponseEntity<>(timeEntriesRepository.create(entry), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable @NotNull Long id) {
        return ofNullable(timeEntriesRepository.find(id))
                .map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntriesRepository.list(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<TimeEntry> update(@PathVariable @NotNull Long id, @RequestBody @NotNull TimeEntry entry) {
        return ofNullable(timeEntriesRepository.update(id, entry))
                .map(updatedEntry -> new ResponseEntity<>(updatedEntry, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable @NotNull Long id) {
        timeEntriesRepository.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
