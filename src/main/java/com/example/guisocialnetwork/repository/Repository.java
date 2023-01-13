package com.example.guisocialnetwork.repository;

import com.example.guisocialnetwork.exceptions.RepositoryException;
/**
 * CRUD operations repository interface
 * @param <ID> id-ul obiectului, tipul ID
 * @param <E> obiectului salvat in repository, tipul E
 */
public interface Repository<ID, E>{
    /**
     * returneaza obiectul cu id-ul specificat din repository
     *
     * @param id, id-ul obiectului pe care dorim sa-l cautam, tipul ID
     * @return obiectul cu id-ul specificat, tipul E
     * @throws RepositoryException daca nu exista obiect cu id-ul dat
     * @throws IllegalArgumentException daca id-ul este null
     */
    E findOne(ID id) throws RepositoryException, IllegalArgumentException;
    /**
     * returneaza lista de obiecte
     * @return lista de obiecte de tipul E, tipul Iterable
     */
    Iterable<E> getAll();

    /**
     * adauga un obiect in repository
     * @param entity obiectul pe care dorim sa-l adaugam, de tipul E
     * @throws RepositoryException daca obiectul a fost deja adaugat
     * @throws IllegalArgumentException daca entity este null
     */
    void add(E entity) throws RepositoryException, IllegalArgumentException;
    /**
     * sterge un obiect din repository
     * @param id id-ul obiectului pe care dorim sa-l stergem, tipul ID
     * @throws RepositoryException daca nu exista obiect cu id-ul specificat
     * @throws IllegalArgumentException daca id-ul este null
     */
    void delete(ID id) throws RepositoryException, IllegalArgumentException;
    /**
     * actualizeaza un obiect in repository
     * @param entity obiectul pe care dorim sa-l actualizam, tipul E
     * @throws RepositoryException daca nu exista obiectul ce se doreste a fi modificat
     * @throws IllegalArgumentException daca entity este null
     */
    void update(E entity) throws RepositoryException, IllegalArgumentException;
    /**
     * returneaza numarul de obiecte din reporistory
     * @return numarul de obiecte, tipul Integer
     */
    int size();
}
