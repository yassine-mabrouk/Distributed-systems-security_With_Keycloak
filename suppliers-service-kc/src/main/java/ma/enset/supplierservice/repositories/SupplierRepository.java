package ma.enset.supplierservice.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ma.enset.supplierservice.entities.Supplier;
@RepositoryRestResource
public interface SupplierRepository extends JpaRepository<Supplier,Long> {
}
