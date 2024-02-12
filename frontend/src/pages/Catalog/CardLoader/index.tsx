import ContentLoader from 'react-content-loader';

const CardLoader = () => (
  <ContentLoader
    speed={2}
    width={400}
    height={460}
    viewBox="0 0 400 460"
    backgroundColor="#a3a3a3"
    foregroundColor="#ecebeb"
  >
    <rect x="0" y="60" rx="2" ry="2" width="300" height="305" />
  </ContentLoader>
);

export default CardLoader;
