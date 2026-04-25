import { IconButton, Tooltip } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

type Props = {
  disabled?: boolean;
  tooltip?: string;
  onClick: () => void;
};

export default function DeleteButton({
  disabled = false,
  tooltip = "Delete",
  onClick,
}: Props) {
  return (
    <Tooltip title={tooltip}>
      <IconButton onClick={onClick} color="primary" disabled={disabled}>
        <DeleteIcon />
      </IconButton>
    </Tooltip>
  );
}
