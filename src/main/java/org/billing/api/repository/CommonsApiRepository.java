package org.billing.api.repository;

import com.sumitchouksey.book.repository.HibernateRepository;
import org.billing.api.entities.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
@Transactional
public class CommonsApiRepository extends HibernateRepository {

    @PersistenceContext
    private EntityManager entityManager;


    public UserEntity addUserEntity(UserEntity userEntity){
        saveOrUpdateEntity(userEntity);
        return userEntity;
    }

    public ClientEntity saveOrUpdateClientEntity(ClientEntity clientEntity){
        saveOrUpdateEntity(clientEntity);
        return clientEntity;
    }


    public UserEntity getUserEntity(String email){
        Session session = entityManager.unwrap(Session.class);
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> userEntityCriteriaQuery= criteriaBuilder.createQuery(UserEntity.class);
        Root<UserEntity> userEntityRoot= userEntityCriteriaQuery.from(UserEntity.class);
        userEntityCriteriaQuery.select(userEntityRoot).distinct(true);
        userEntityCriteriaQuery
                .where(
                        criteriaBuilder.equal(userEntityRoot.get(UserEntity_.email),email)
                );
        List<UserEntity> userEntities= entityManager.createQuery( userEntityCriteriaQuery )
                .getResultList();
        if(userEntities!=null)
        {
            if(!userEntities.isEmpty())
                return userEntities.get(0);
        }
        return null;
    }


    public UserEntity getUserEntity(Long userId){
        return (UserEntity) getEntity(UserEntity.class,userId);
    }


    public ClientEntity getClientEntity(Long clientId)
    {
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> clientEntityCriteriaQuery= criteriaBuilder.createQuery(ClientEntity.class);
        Root<ClientEntity> clientEntityRoot= clientEntityCriteriaQuery.from(ClientEntity.class);
        clientEntityCriteriaQuery.select(clientEntityRoot).distinct(true);
        clientEntityCriteriaQuery
                .where(
                        criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.id),clientId),
                        criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.isActive),true)
                );
        List<ClientEntity> clientEntities= entityManager.createQuery( clientEntityCriteriaQuery )
                .getResultList();
        if(clientEntities!=null)
        {
            if(!clientEntities.isEmpty())
                return clientEntities.get(0);
        }
        return null;
    }



    public ClientEntity getClientEntity(String clientName)
    {
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEntity> clientEntityCriteriaQuery= criteriaBuilder.createQuery(ClientEntity.class);
        Root<ClientEntity> clientEntityRoot= clientEntityCriteriaQuery.from(ClientEntity.class);
        clientEntityCriteriaQuery.select(clientEntityRoot).distinct(true);
        clientEntityCriteriaQuery
                .where(
                        criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.clientName),clientName),
                        criteriaBuilder.equal(clientEntityRoot.get(ClientEntity_.isActive),true)
                );
        List<ClientEntity> clientEntities= entityManager.createQuery( clientEntityCriteriaQuery )
                .getResultList();
        if(clientEntities!=null)
        {
            if(!clientEntities.isEmpty())
                return clientEntities.get(0);
        }
        return null;
    }


    public ProductEntity getProductEntity(String productName)
    {
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductEntity> productEntityCriteriaQuery= criteriaBuilder.createQuery(ProductEntity.class);
        Root<ProductEntity> productEntityRoot= productEntityCriteriaQuery.from(ProductEntity.class);
        productEntityCriteriaQuery.select(productEntityRoot).distinct(true);
        productEntityCriteriaQuery
                .where(
                        criteriaBuilder.equal(productEntityRoot.get(ProductEntity_.productName),productName),
                        criteriaBuilder.equal(productEntityRoot.get(ProductEntity_.isActive),true)
                );
        List<ProductEntity> productEntities= entityManager.createQuery( productEntityCriteriaQuery )
                .getResultList();
        if(productEntities!=null)
        {
            if(!productEntities.isEmpty())
                return productEntities.get(0);
        }
        return null;
    }



    public TokenVerificationEntity addTokenVerificationEntity(TokenVerificationEntity tokenVerificationEntity){
        saveOrUpdateEntity(tokenVerificationEntity);
        return tokenVerificationEntity;
    }

    public TokenVerificationEntity getTokenVerificationEntity(String token){
        CriteriaBuilder criteriaBuilder  = entityManager.getCriteriaBuilder();
        CriteriaQuery<TokenVerificationEntity> tokenVerificationEntityCriteriaQuery= criteriaBuilder.createQuery(TokenVerificationEntity.class);
        Root<TokenVerificationEntity> tokenVerificationEntityRoot= tokenVerificationEntityCriteriaQuery.from(TokenVerificationEntity.class);
        tokenVerificationEntityCriteriaQuery.select(tokenVerificationEntityRoot).distinct(true);
        tokenVerificationEntityCriteriaQuery
                .where(
                        criteriaBuilder.equal(tokenVerificationEntityRoot.get(TokenVerificationEntity_.token),token),
                        criteriaBuilder.equal(tokenVerificationEntityRoot.get(TokenVerificationEntity_.isActive),true)
                );
        List<TokenVerificationEntity> tokenVerificationEntities= entityManager.createQuery( tokenVerificationEntityCriteriaQuery )
                .getResultList();
        if(tokenVerificationEntities!=null)
        {
            if(!tokenVerificationEntities.isEmpty())
                return tokenVerificationEntities.get(0);
        }
        return null;
    }

    /**
     * Update current user entity
     * @param userEntity
     */
    public void updateUserEntity(UserEntity userEntity){
        updateEntity(userEntity);
    }

}
