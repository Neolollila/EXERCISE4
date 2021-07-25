package net.mandrik;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u WHERE u.email = ?1 and u.status = ?2")
	public User findByEmail(String email,String status);

	@Modifying
	@Transactional
	@Query("delete from User u where u.id in ?1")
	void deleteUsersWithIds(List<Long> ids);

	@Modifying
	@Transactional
	@Query("update User u set u.status = ?1 where u.id in ?2")
	void setStatusForUsersByIds(String status, List<Long> ids);

	@Query("SELECT u FROM User u WHERE u.id = ?1")
	public User findByIdCurUser(Long id);
}
