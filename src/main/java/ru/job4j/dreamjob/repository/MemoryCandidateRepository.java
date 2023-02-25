package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.model.Vacancy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryCandidateRepository implements CandidateRepository {

    private static final MemoryCandidateRepository INSTANCE = new MemoryCandidateRepository();

    private int nextId = 1;

    private final Map<Integer, Candidate> candidates = new HashMap<>();

    public static MemoryCandidateRepository getInstance() {
        return INSTANCE;
    }

    private MemoryCandidateRepository() {
        save(new Candidate(0, "Lana Sergeeva", "Intern Java Developer"));
        save(new Candidate(0, "Stas Korobeinikov", "Junior Java Developer"));
        save(new Candidate(0, "Mikhail Lukonin", "Junior+ Java Developer"));
        save(new Candidate(0, "Petr Arsentev", "Middle Java Developer"));
        save(new Candidate(0, "Alexey Esipov", "Middle+ Java Developer"));
        save(new Candidate(0, "Rail Shamsemuhametov", "Senior Java Developer"));
    }

    @Override
    public Candidate save(Candidate candidate) {
        candidate.setId(nextId++);
        candidates.put(candidate.getId(), candidate);
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return candidates.remove(id) != null;
    }

    @Override
    public boolean update(Candidate candidate) {
        return candidates.computeIfPresent(candidate.getId(), (id, oldCandidate) ->
                new Candidate(oldCandidate.getId(), candidate.getName(), candidate.getDescription())) != null;
    }

    @Override
    public Optional<Candidate> findById(int id) {
        return Optional.ofNullable(candidates.get(id));
    }

    @Override
    public Collection<Candidate> findAll() {
        return candidates.values();
    }
}