import './styles.css';
import ArrowIcon from '../../assets/imgs/Seta.svg';

type Props = {
  text: string;
}

const ButtonIcon = ({text}: Props) => {
  return (
    <div className="btn-container">
      <button className="btn btn-primary">
        <h6>{text}</h6>
      </button>

      <div className="btn-icon-container">
        <img src={ArrowIcon} alt="" />
      </div>
    </div>
  );
};

export default ButtonIcon;
