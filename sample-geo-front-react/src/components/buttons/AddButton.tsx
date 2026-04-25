import { IconButton, Tooltip } from "@mui/material";
import AddCircleIcon from "@mui/icons-material/AddCircle";

type Props = {
  disabled?: boolean;
  tooltip?: string;
  onClick: () => void;
};

export default function AddButton({
  disabled = false,
  tooltip = "Add",
  onClick,
}: Props) {
  return (
    <Tooltip title={tooltip}>
      <IconButton onClick={onClick} color="primary" disabled={disabled}>
        <AddCircleIcon />
      </IconButton>
    </Tooltip>
  );
}
