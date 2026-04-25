import { IconButton, Tooltip } from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";

type Props = {
  disabled?: boolean;
  tooltip?: string;
  onClick: () => void;
};

export default function EditButton({
  disabled = false,
  tooltip = "Edit",
  onClick,
}: Props) {
  return (
    <Tooltip title={tooltip}>
      <IconButton onClick={onClick} color="primary" disabled={disabled}>
        <EditIcon />
      </IconButton>
    </Tooltip>
  );
}
