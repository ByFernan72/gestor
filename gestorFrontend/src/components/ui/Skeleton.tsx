import React from 'react';
import './Skeleton.css';

interface SkeletonProps {
  className?: string;
  width?: string | number;
  height?: string | number;
  borderRadius?: string | number;
  style?: React.CSSProperties;
}

export const Skeleton: React.FC<SkeletonProps> = ({
  className = '',
  width,
  height,
  borderRadius,
  style,
}) => {
  const customStyle: React.CSSProperties = {
    width: width ?? '100%',
    height: height ?? '20px',
    borderRadius: borderRadius ?? '8px',
    ...style,
  };

  return <div className={`skeleton-loader ${className}`} style={customStyle} />;
};

export const DashboardSkeleton: React.FC = () => {
  return (
    <div className="dashboard-container skeleton-dashboard" data-testid="dashboard-skeleton">
      <div className="home-left-col">
        <div className="dashboard-card">
          <Skeleton width="120px" height="18px" style={{ marginBottom: '16px' }} />
          <Skeleton width="180px" height="36px" borderRadius="10px" />
        </div>
        <div className="dashboard-card">
          <Skeleton width="140px" height="18px" style={{ marginBottom: '20px' }} />
          <div style={{ display: 'flex', flexDirection: 'column', gap: '14px' }}>
            <Skeleton height="45px" borderRadius="12px" />
            <Skeleton height="45px" borderRadius="12px" />
            <Skeleton height="45px" borderRadius="12px" />
          </div>
        </div>
      </div>
      <div className="home-right-col">
        <div className="dashboard-card transactions-card">
          <Skeleton width="180px" height="18px" style={{ marginBottom: '20px' }} />
          <div style={{ display: 'flex', flexDirection: 'column', gap: '12px' }}>
            <Skeleton height="35px" borderRadius="8px" />
            <Skeleton height="35px" borderRadius="8px" />
            <Skeleton height="35px" borderRadius="8px" />
            <Skeleton height="35px" borderRadius="8px" />
          </div>
        </div>
      </div>
    </div>
  );
};
