package com.example.collectionspractice.common

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * 역할:
 * - 이 클래스를 상속한 엔티티는 created_at, updated_at 컬럼을 자동으로 가진다.
 * - INSERT 시 created_at, updated_at 둘 다 채워짐
 * - UPDATE 시 updated_at이 자동으로 갱신됨
 */
@MappedSuperclass // 실제 테이블로 생성되지 않고, 하위 엔티티의 컬럼으로 합쳐짐
@EntityListeners(AuditingEntityListener::class) // Audit 라이프사이클 리스너 연결
abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    var createdAt: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime? = null
}