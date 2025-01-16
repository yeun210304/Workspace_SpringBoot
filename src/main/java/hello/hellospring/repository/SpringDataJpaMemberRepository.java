package hello.hellospring.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import hello.hellospring.domain.Member  ;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    @SuppressWarnings("unchecked")
    @Override
    public Member save(Member member);

    @Override
    Optional<Member> findById(Long id);

    @Override
    Optional<Member> findByName(String name);

    @Override
    List<Member> findAll();

    @Override
    void deleteById(Long id);
}