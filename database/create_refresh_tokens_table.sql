-- =============================================
-- Script: Tạo bảng refresh_tokens cho JWT
-- Database: SQL Server
-- Author: Student Management System
-- Date: 2025-12-25
-- =============================================

USE [db_quanlyhocsinh_v2];
GO

-- Xóa bảng nếu đã tồn tại (CHỈ DÙNG CHO DEVELOPMENT)
-- COMMENT OUT dòng này khi chạy trên PRODUCTION
IF OBJECT_ID('dbo.refresh_tokens', 'U') IS NOT NULL
    DROP TABLE dbo.refresh_tokens;
GO

-- Tạo bảng refresh_tokens
CREATE TABLE dbo.refresh_tokens (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    token_id VARCHAR(255) NOT NULL UNIQUE,  -- UUID của token
    user_id INT NOT NULL,                   -- Foreign key tới TaiKhoan
    token_hash VARCHAR(500) NOT NULL,       -- SHA-256 hash của token
    expires_at DATETIME NOT NULL,           -- Thời điểm hết hạn
    created_at DATETIME DEFAULT GETDATE(),  -- Thời điểm tạo
    revoked BIT DEFAULT 0,                  -- Token đã bị thu hồi chưa
    
    -- Foreign key constraint
    CONSTRAINT FK_refresh_tokens_user 
        FOREIGN KEY (user_id) 
        REFERENCES dbo.TaiKhoan(maTK)
        ON DELETE CASCADE  -- Khi xóa user, xóa luôn các tokens
);
GO

-- Tạo indexes để tăng performance
CREATE INDEX idx_token_id ON dbo.refresh_tokens(token_id);
GO

CREATE INDEX idx_user_id ON dbo.refresh_tokens(user_id);
GO

CREATE INDEX idx_expires_at ON dbo.refresh_tokens(expires_at);
GO

CREATE INDEX idx_revoked ON dbo.refresh_tokens(revoked);
GO

-- Tạo composite index cho query thông dụng
CREATE INDEX idx_user_active ON dbo.refresh_tokens(user_id, revoked, expires_at);
GO

PRINT '✓ Table refresh_tokens created successfully';
PRINT '✓ Indexes created successfully';
GO

-- Kiểm tra cấu trúc bảng
SELECT 
    COLUMN_NAME,
    DATA_TYPE,
    CHARACTER_MAXIMUM_LENGTH,
    IS_NULLABLE,
    COLUMN_DEFAULT
FROM INFORMATION_SCHEMA.COLUMNS
WHERE TABLE_NAME = 'refresh_tokens'
ORDER BY ORDINAL_POSITION;
GO

-- =============================================
-- STORED PROCEDURE: Cleanup expired tokens
-- Tự động xóa các tokens đã hết hạn
-- =============================================

CREATE OR ALTER PROCEDURE sp_CleanupExpiredTokens
AS
BEGIN
    SET NOCOUNT ON;
    
    DECLARE @DeletedCount INT;
    
    -- Xóa tokens đã hết hạn
    DELETE FROM dbo.refresh_tokens
    WHERE expires_at < GETDATE();
    
    SET @DeletedCount = @@ROWCOUNT;
    
    PRINT 'Cleaned up ' + CAST(@DeletedCount AS VARCHAR(10)) + ' expired tokens';
    
    RETURN @DeletedCount;
END;
GO

PRINT '✓ Stored procedure sp_CleanupExpiredTokens created successfully';
GO

-- =============================================
-- SQL JOB: Tự động cleanup mỗi ngày (OPTIONAL)
-- Cần SQL Server Agent enabled
-- =============================================

/*
-- Uncomment để tạo scheduled job

USE msdb;
GO

-- Tạo job cleanup tokens hàng ngày
EXEC sp_add_job
    @job_name = N'Daily_Cleanup_Expired_Tokens',
    @enabled = 1,
    @description = N'Clean up expired refresh tokens daily';
GO

-- Thêm job step
EXEC sp_add_jobstep
    @job_name = N'Daily_Cleanup_Expired_Tokens',
    @step_name = N'Cleanup Step',
    @subsystem = N'TSQL',
    @command = N'EXEC student_management.dbo.sp_CleanupExpiredTokens',
    @database_name = N'student_management';
GO

-- Schedule: Chạy mỗi ngày lúc 2:00 AM
EXEC sp_add_schedule
    @schedule_name = N'Daily at 2 AM',
    @freq_type = 4,  -- Daily
    @freq_interval = 1,
    @active_start_time = 020000;  -- 02:00:00
GO

-- Attach schedule to job
EXEC sp_attach_schedule
    @job_name = N'Daily_Cleanup_Expired_Tokens',
    @schedule_name = N'Daily at 2 AM';
GO

-- Add job to local server
EXEC sp_add_jobserver
    @job_name = N'Daily_Cleanup_Expired_Tokens';
GO

PRINT '✓ Scheduled job created successfully';
*/

-- =============================================
-- TEST DATA (OPTIONAL - CHỈ DÙNG CHO TESTING)
-- =============================================

/*
-- Insert test refresh token
INSERT INTO dbo.refresh_tokens (token_id, user_id, token_hash, expires_at, revoked)
VALUES 
    ('test-uuid-1234', 1, 'test-hash-value', DATEADD(DAY, 7, GETDATE()), 0);

SELECT * FROM dbo.refresh_tokens;
*/

PRINT '';
PRINT '========================================';
PRINT 'JWT Refresh Tokens Table Setup Complete';
PRINT '========================================';
PRINT '';
PRINT 'Next steps:';
PRINT '1. Update your database connection settings';
PRINT '2. Build and deploy the application';
PRINT '3. Test the JWT authentication endpoints';
PRINT '4. (Optional) Enable SQL Server Agent for automatic cleanup';
PRINT '';
GO
