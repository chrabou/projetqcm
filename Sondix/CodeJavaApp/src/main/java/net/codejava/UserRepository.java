package net.codejava;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1")
	public User findByEmail(String email);

	@Query(value = "SELECT * FROM users u WHERE  u.email = ?1", nativeQuery = true)
	public User findByEmail2(String email);

	@Query(value = "SELECT count(*) FROM users u WHERE  u.role = 'Internaute'", nativeQuery = true)
	public String countTotalInternaute();

}
