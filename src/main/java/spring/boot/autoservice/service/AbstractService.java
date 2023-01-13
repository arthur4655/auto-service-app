package spring.boot.autoservice.service;

public interface AbstractService<T, P> {
    T save(T o);

    T update(P id, T o);

    T get(P id);
}
