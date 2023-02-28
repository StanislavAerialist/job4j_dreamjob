package ru.job4j.dreamjob.repository;

import org.apache.http.annotation.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
@Repository
public class MemoryVacancyRepository implements VacancyRepository {

    private final AtomicInteger nextId = new AtomicInteger(1);
    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    private MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer", "Just learn", LocalDateTime.now(), true, 1));
        save(new Vacancy(0, "Junior Java Developer", "Knew some things", LocalDateTime.now(), true, 1));
        save(new Vacancy(0, "Junior+ Java Developer", "Knew basic things", LocalDateTime.now(), true, 2));
        save(new Vacancy(0, "Middle Java Developer", "Knew basic things and algorithms", LocalDateTime.now(), true, 2));
        save(new Vacancy(0, "Middle+ Java Developer", "Knew advanced things", LocalDateTime.now(), true, 3));
        save(new Vacancy(0, "Senior Java Developer", "Knew advanced things and architecture", LocalDateTime.now(), true, 3));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId.getAndIncrement());
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(vacancy.getId(), (id, oldVacancy) -> {
            return new Vacancy(oldVacancy.getId(), vacancy.getTitle(), vacancy.getDescription(),
                    vacancy.getCreationDate(), vacancy.getVisible(), vacancy.getCityId());
        }) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }

}
